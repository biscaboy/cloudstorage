package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.mapper.SuperDuperFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Validate the (Super Duper) File Model and Data Mappings")
@MybatisTest
public class SuperDuperFileModelTest {

    @Autowired
    private SuperDuperFileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    // Testing files read/written on the local filesystem to stand in for uploaded files from a browser.
    private static String pathToUploadPdf = "src/test/resources/Puppies-in.pdf";
    private static String pathToOutputPdf = "src/test/resources/Puppies-out.pdf";
    private static String pathToUploadDoc = "src/test/resources/Puppies-in.docx";
    private static String pathToOutputDoc = "src/test/resources/Puppies-out.docx";

    // Test files
    private static File uploadFile;
    private static File outputFile;

    // User
    private static String username = "junit";
    private static String salt = "salty";
    private static String password = "password";
    private static String firstName = "Jay";
    private static String lastName = "Unit";
    private static User testUser;

    // File
    private SuperDuperFile testFile;

    @BeforeAll
    public static void beforeAll() {
        testUser = new User(null, username, salt, password, firstName, lastName);
        uploadFile = new File(pathToUploadPdf);
        outputFile = new File(pathToOutputPdf);
    }

    @BeforeEach
    public void beforeEach() {
        userMapper.insert(testUser);
        // retrieve the user from the database to populate the userId
        testUser = userMapper.getUser(testUser.getUsername());
        try {
            byte [] fileContents = Files.readAllBytes(uploadFile.toPath());
            String contentType = Files.probeContentType(uploadFile.toPath());
            testFile = new SuperDuperFile(null, uploadFile.getName(), contentType, String.valueOf(uploadFile.length()), testUser.getUserId(), fileContents);
        } catch (IOException ioe) {
            fail("Missing test upload file.  Expected path: " + uploadFile.getPath());
        }

    }

    @AfterEach
    public void afterEach() {
        // @TODO Figure out if there is a way to reset the database state with an Annotations (@DirtiesContext?).
        fileMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {
        @Test
        @DisplayName("Save (Insert) a super duper file")
        public void testInsertFile(){
            int result = fileMapper.insert(testFile);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Get a super duper file.")
        public void testGetFile(){
            // clean up any previous file creation.
            if (outputFile.delete()){
                System.out.println("Removed existing working file " + outputFile.getPath());
            }

            int result = fileMapper.insert(testFile);
            assertEquals(1, result, "The file insert failed.");
            ArrayList<SuperDuperFile> files = fileMapper.getFiles(testUser);
            assertAll("(super duper) file",
                    () -> assertEquals(1, files.size(), "The file was not returned from the database."),
                    () -> assertNotNull(files.get(0).getFileId()),
                    () -> assertEquals(testFile.getName(), files.get(0).getName()),
                    () -> assertEquals(testFile.getContentType(), files.get(0).getContentType()),
                    () -> assertEquals(testFile.getSize(), files.get(0).getSize()),
                    () -> assertEquals(testFile.getUserId(), files.get(0).getUserId()),
                    () -> assertEquals(testFile.getData().length, files.get(0).getData().length)
            );

            try (FileOutputStream fos = new FileOutputStream(outputFile)){
                fos.write(files.get(0).getData());
            } catch (Exception e){
                fail("Failed to write file " + outputFile.getPath() + " : " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Update an existing super duper file.")
        public void testUpdateFile(){

            // insert a file to get a file id.
            int result = fileMapper.insert(testFile);
            assertEquals(1, result);

            // instantiate a new note with the new id and update title and desc.
            SuperDuperFile testFile = fileMapper.getFiles(testUser).get(0);

            // update the pdf file to be a Word document
            try {
                File f = new File(pathToUploadDoc);
                testFile.setContentType(Files.probeContentType(f.toPath()));
                testFile.setName(f.getName());
                testFile.setSize(String.valueOf(f.length()));
                testFile.setData(Files.readAllBytes(f.toPath()));
            } catch (IOException ioe) {
                fail("Missing test update file.  Expected path: " + pathToUploadDoc);
            }

            // save the file
            result = fileMapper.update(testFile);
            assertEquals(1, result, "The file update failed.");
            ArrayList<SuperDuperFile> files = fileMapper.getFiles(testUser);
            assertAll("(super duper) file",
                    () -> assertEquals(1, files.size(), "The file was not returned from the database."),
                    () -> assertNotNull(files.get(0).getFileId()),
                    () -> assertEquals(testFile.getName(), files.get(0).getName()),
                    () -> assertEquals(testFile.getContentType(), files.get(0).getContentType()),
                    () -> assertEquals(testFile.getSize(), files.get(0).getSize()),
                    () -> assertEquals(testFile.getUserId(), files.get(0).getUserId()),
                    () -> assertEquals(testFile.getData().length, files.get(0).getData().length)
            );

            // delete the output file from previous test runs.
            try {
                if (Files.exists(Path.of(pathToOutputDoc))) {
                    Files.delete(Path.of(pathToOutputDoc));
                }
            } catch (Exception e) {
                fail("Failed to delete existing file " + pathToOutputDoc + " : " + e.getMessage());
            }
            try (FileOutputStream fos = new FileOutputStream(pathToOutputDoc)){
                fos.write(files.get(0).getData());
            } catch (Exception e){
                fail("Failed to write file " + pathToOutputDoc + " : " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Delete a super duper file.")
        public void testDeleteFile() {
            // insert two files
            fileMapper.insert(testFile);
            testFile.setName("Another File.pdf");
            fileMapper.insert(testFile);

            // make sure the file was inserted properly
            ArrayList<SuperDuperFile> files = fileMapper.getFiles(testUser);
            assertEquals(2, files.size());

            // delete the files by id
            Integer idToDelete = files.get(0).getFileId();
            int result = fileMapper.deleteFile(idToDelete);
            assertEquals(1, result);

            // make sure the right files was deleted.
            files = fileMapper.getFiles(testUser);
            assertEquals(1, files.size());
            assertNotEquals(idToDelete, files.get(0).getFileId(), "Deleted the wrong file.");
        }

        @Test
        @DisplayName("Delete all super duper files for a user.")
        public void testDeleteUsersFiles(){
            // insert the same file twice
            int result = fileMapper.insert(testFile);
            assertEquals(1, result);
            testFile.setName("File 2.pdf");
            result = fileMapper.insert(testFile);
            assertEquals(1, result);

            // make sure both files were inserted properly
            ArrayList<SuperDuperFile> files = fileMapper.getFiles(testUser);
            assertEquals(2, files.size());

            // delete the files for this user
            result = fileMapper.deleteFiles(testUser);
            assertEquals(2, result);

            // verify all notes were deleted.
            ArrayList<SuperDuperFile> noFiles = fileMapper.getFiles(testUser);
            assertEquals(0, noFiles.size());
        }
    }
}
