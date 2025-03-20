package com.uplift.system.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoryTellerTest {

    @Test
    void testInteractiveNarrative() {
        // This test is more of an integration test and requires user input.
        // It's difficult to automate fully without mocking the input stream.
        // For now, we'll just check that the StoryTeller can be instantiated
        // and that the interactiveNarrative method can be called without throwing an exception.
        StoryTeller storyTeller = new StoryTeller();
        assertDoesNotThrow(storyTeller::interactiveNarrative, "interactiveNarrative should not throw an exception");
    }

    @Test
    void testGenerateNarrative() {
        // Test the generateNarrative method.
        // We'll create a Librarian with a simple registry and check that
        // the StoryTeller generates a narrative.
        Librarian librarian = new Librarian("config/test_registry.yaml");
        StoryTeller storyTeller = new StoryTeller(librarian);
        String narrative = storyTeller.generateNarrative();
        assertNotNull(narrative, "generateNarrative should return a non-null string");
        assertFalse(narrative.isEmpty(), "generateNarrative should return a non-empty string");
    }

    @Test
    void testGenerateNarrativeWithContext() {
        // Test the generateNarrative method with a context.
        Librarian librarian = new Librarian("config/test_registry.yaml");
        StoryTeller storyTeller = new StoryTeller(librarian);
        String context = "Testing context";
        String narrative = storyTeller.generateNarrative(context);
        assertNotNull(narrative, "generateNarrative should return a non-null string");
        assertFalse(narrative.isEmpty(), "generateNarrative should return a non-empty string");
        assertTrue(narrative.contains(context), "Narrative should contain the context");
    }
} 