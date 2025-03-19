#!/usr/bin/env python3
"""
Story Teller: User Interaction Interface

This module provides an interactive interface for users to explore
the system's metaphorical and technical narratives.
"""

from typing import List, Dict, Any
from library.librarian import Librarian

class StoryTeller:
    def __init__(self, librarian: Librarian = None):
        """
        Initialize the StoryTeller with an optional Librarian instance.
        
        Args:
            librarian (Librarian, optional): Librarian instance to use. 
                                             Creates a new one if not provided.
        """
        self.librarian = librarian or Librarian()
    
    def interactive_narrative(self) -> None:
        """
        Provide an interactive narrative exploration of the system.
        """
        print("Welcome to the Nested Librarian System")
        print("Exploring the realms of technological interoperability...\n")
        
        while True:
            print("Choose an exploration path:")
            print("1. System Overview")
            print("2. Technology Gate Details")
            print("3. Creative Principles")
            print("4. Exit")
            
            choice = input("Enter your choice (1-4): ")
            
            if choice == '1':
                print(self.librarian.tell_story())
            elif choice == '2':
                self._explore_technologies()
            elif choice == '3':
                self._explore_creative_principles()
            elif choice == '4':
                print("Farewell, traveler of technological realms!")
                break
            else:
                print("Invalid choice. Please try again.")
    
    def _explore_technologies(self) -> None:
        """
        Explore and display details about system technologies.
        """
        technologies = self.librarian.registry.get('components', {}).get('gate', {}).get('technologies', [])
        
        print("\n--- Technology Gate Details ---")
        for tech in technologies:
            print(f"\n{tech['name']} Technology:")
            print(f"  Role: {tech['role']}")
            print(f"  Version: {tech['version']}")
    
    def _explore_creative_principles(self) -> None:
        """
        Display and explain the system's creative principles.
        """
        principles = self.librarian.registry.get('creative_principles', [])
        
        print("\n--- Creative Principles ---")
        for principle in principles:
            for name, details in principle.items():
                print(f"\n{name.replace('_', ' ').title()}:")
                print(f"  {details['description']}")
    
    def generate_narrative(self, context: str = None) -> str:
        """
        Generate a narrative based on the system's current state.
        
        Args:
            context (str, optional): Specific context for the narrative.
        
        Returns:
            str: Generated narrative.
        """
        return self.librarian.tell_story(context)

def main():
    """
    Main entry point for the StoryTeller.
    """
    story_teller = StoryTeller()
    story_teller.interactive_narrative()

if __name__ == "__main__":
    main() 