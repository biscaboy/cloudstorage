package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Validate Note Model and Data Mappings")
@MybatisTest
public class NoteModelTest {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserMapper userMapper;

    private String title = "My Story";
    private String description = "Once upon a time...";
    private Note testNote;

    // User
    private static String username = "junit";
    private static String salt = "salty";
    private static String password = "password";
    private static String firstName = "Jay";
    private static String lastName = "Unit";
    private static User testUser;

    @BeforeAll
    public static void beforeAll() {
        testUser = new User(null, username, salt, password, firstName, lastName);
    }

    @BeforeEach
    public void beforeEach() {
        userMapper.insert(testUser);
        // retrieve the user from the database to populate the userId
        testUser = userMapper.getUser(testUser.getUsername());
        testNote = new Note(null, title, description, testUser.getUserId());
    }

    @AfterEach
    public void afterEach() {
        // @TODO Figure out if there is a way to reset the database state with an Annotations (@DirtiesContext?).
        noteMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {
        @Test
        @DisplayName("Create a new note")
        public void testCreateNote(){
            int result = noteMapper.insert(testNote);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Get a note.")
        public void testGetNote(){
            int result = noteMapper.insert(testNote);
            assertEquals(1, result);
            ArrayList<Note> notes = noteMapper.getNotes(testUser);
            assertAll("note",
                    () -> assertEquals(1, notes.size()),
                    () -> assertNotNull(notes.get(0).getNoteId()),
                    () -> assertEquals(testNote.getNoteTitle(), notes.get(0).getNoteTitle()),
                    () -> assertEquals(testNote.getNoteDescription(), notes.get(0).getNoteDescription()),
                    () -> assertEquals(testNote.getUserId(), notes.get(0).getUserId())
            );
        }

        @Test
        @DisplayName("Update an existing note.")
        public void testUpdateNote(){
            // insert a note to get a note id.
            int result = noteMapper.insert(testNote);
            assertEquals(1, result);

            // instantiate a new note with the new id and update title and desc.
            ArrayList<Note> notes = noteMapper.getNotes(testUser);
            Note newNote = notes.get(0);
            newNote.setNoteTitle("Things to do");
            newNote.setNoteDescription("Go to the store. Buy carrots.");
            int newResult = noteMapper.update(newNote);
            assertEquals(1, result);

            // get the updated note and verify it was updated.
            ArrayList<Note> updatedNotes = noteMapper.getNotes(testUser);
            Note updatedNote = updatedNotes.get(0);
            assertAll("note",
                    () -> assertEquals(1, updatedNotes.size()),
                    () -> assertEquals(newNote.getNoteId(), updatedNote.getNoteId()),
                    () -> assertEquals(newNote.getNoteTitle(), updatedNote.getNoteTitle()),
                    () -> assertEquals(newNote.getNoteDescription(), updatedNote.getNoteDescription()),
                    () -> assertEquals(newNote.getUserId(), updatedNote.getUserId())
            );
        }

        @Test
        @DisplayName("Delete a note.")
        public void testDeleteNote(){
            int result = noteMapper.insert(testNote);
            assertEquals(1, result);

            ArrayList<Note> notes = noteMapper.getNotes(testUser);

            result = noteMapper.delete(notes.get(0));
            assertEquals(1, result);

            ArrayList<Note> noNotes = noteMapper.getNotes(testUser);
            assertEquals(0, noNotes.size());
        }

        @Test
        @DisplayName("Delete all user's notes.")
        public void testDeleteUserNotes(){
            int result = noteMapper.insert(testNote);
            assertEquals(1, result);
            result = noteMapper.insert(testNote);
            assertEquals(1, result);

            // make sure both notes were inserted properly
            ArrayList<Note> notes = noteMapper.getNotes(testUser);
            assertEquals(2, notes.size());

            // delete the notes
            result = noteMapper.deleteNotes(testUser);
            assertEquals(2, result);

            // verify all notes were deleted.
            ArrayList<Note> noNotes = noteMapper.getNotes(testUser);
            assertEquals(0, noNotes.size());
        }
    }
}
