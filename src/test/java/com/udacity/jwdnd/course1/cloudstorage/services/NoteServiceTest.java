package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    // User
    private static User testUser1;
    private static User testUser2;

    // Note
    private String [][] testNotes = {
                {"Notes from Home","Always away are you. Never are you near, but always are you dyspeptic."},
                {"Cooking Dinner Ideas", "Ramen noodles for breakfast, lunch and dinner."},
                {"Blue Ribbon Award", "Give another look at Rina and Rom team."}
            };
    private HashMap<String, ArrayList<Note>> testNoteMap;
    private ArrayList<User> testUserList;

    @BeforeEach
    public void beforeEach() {
        testUser1 = new User(null, "junit", null, "password", "Jay", "Unit");
        testUser2 = new User(null, "rtinu", null, "password", "Ray", "Tinu");
        userService.createUser(testUser1);
        userService.createUser(testUser2);

        testUserList = new ArrayList<>();
        testUserList.add(userService.getUser(testUser1.getUsername()));
        testUserList.add(userService.getUser(testUser2.getUsername()));

        testNoteMap = new HashMap<>();
        for (User user : testUserList) {
            ArrayList<Note> userNotes = new ArrayList<>();
            for (int i = 0; i < testNotes.length; i++) {
                Note n = new Note(null, testNotes[i][0], testNotes[i][1], user.getUserId());
                userNotes.add(n);
            }
            testNoteMap.put(user.getUsername(), userNotes);
        }
    }

    @AfterEach
    public void afterEach() {
        noteService.deleteAllNotes();
        userService.deleteAllUsers();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {
        @Test
        @DisplayName("Create a note.")
        public void testCreateNote() {
            ArrayList<Note> testNotes = testNoteMap.get(testUser1.getUsername());
            Note n = testNotes.get(0);
            int result = noteService.createNote(n);
            assertEquals(1, result, "Failed to create new note.");
        }

        @Test
        @DisplayName("Get a user's notes.")
        public void testGetUsersNotes() {
            // create some notes for user
            String username1 = testUserList.get(0).getUsername();
            for (Note note : testNoteMap.get(username1)) {
                int result = noteService.createNote(note);
                assertEquals(1, result);
            }
            ArrayList<Note> notes = noteService.getNotes(testUserList.get(0));
            assertAll("Get notes for user '" + testUserList.get(0) + "'.",
                    () -> assertEquals(3, notes.size()),
                    () -> assertNotNull(notes.get(0).getId()),
                    () -> assertEquals(notes.get(0).getId(), testNoteMap.get(username1).get(0).getId()),
                    () -> assertEquals(notes.get(0).getUserId(), testUserList.get(0).getUserId()),
                    () -> assertEquals(notes.get(0).getTitle(), testNoteMap.get(username1).get(0).getTitle()),
                    () -> assertEquals(notes.get(0).getDescription(), testNoteMap.get(username1).get(0).getDescription()),
                    () -> assertNotNull(notes.get(2).getId()),
                    () -> assertEquals(notes.get(2).getId(), testNoteMap.get(username1).get(2).getId()),
                    () -> assertEquals(notes.get(2).getUserId(), testNoteMap.get(username1).get(2).getUserId()),
                    () -> assertEquals(notes.get(2).getTitle(), testNoteMap.get(username1).get(2).getTitle()),
                    () -> assertEquals(notes.get(2).getDescription(), testNoteMap.get(username1).get(2).getDescription())
            );
        }

        @Test
        @DisplayName("Delete a note.")
        public void testDeleteNote() {
            // create a test note
            Note n = testNoteMap.get(testUserList.get(0).getUsername()).get(0);
            int result = noteService.createNote(n);
            assertEquals(1, result, "Failed to create new note.");

            // retrieve the note from the database
            n = noteService.getNotes(testUserList.get(0)).get(0);

            // delete
            boolean boolResult = noteService.deleteNote(n);
            assertTrue(boolResult, "Failed to delete saved note.");
        }
    }
}
