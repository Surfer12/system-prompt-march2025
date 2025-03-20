#!/usr/bin/env python3
"""
The Librarian: Central Orchestration Module

This module represents the librarian in our metaphorical system,
responsible for navigating and describing all system components.
"""

try:
    import yaml
except ImportError:
    print("PyYAML package not installed. Please run 'pip install PyYAML'")
    raise
from typing import Dict, Any

class Librarian:
    def __init__(self, registry_path: str = 'library/registry.yaml'):
        """
        Initialize the Librarian with the system's registry.

        Args:
            registry_path (str): Path to the system's configuration registry.
        """
        self.registry = self._load_registry(registry_path)

    def _load_registry(self, registry_path: str) -> Dict[str, Any]:
        """
        Load the system registry from a YAML file.

        Args:
            registry_path (str): Path to the registry YAML file.

        Returns:
            Dict[str, Any]: Loaded registry configuration.
        """
        try:
            with open(registry_path, 'r') as file:
                return yaml.safe_load(file)
        except FileNotFoundError:
            print(f"Registry not found at {registry_path}. Creating an empty registry.")
            return {}
        except yaml.YAMLError as e:
            print(f"Error parsing registry: {e}")
            return {}

    def tell_story(self, context: str = None) -> str:
        """
        Metaphorical method to describe system state and interactions.

        Args:
            context (str, optional): Specific context for the story.

        Returns:
            str: A narrative description of the system.
        """
        # Implement a storytelling mechanism that describes system state
        base_story = "In the library of technologies, where gates connect different realms..."

        if context:
            base_story += f"\nToday's tale revolves around: {context}"

        return base_story

    def get_component_details(self, component_name: str) -> Dict[str, Any]:
        """
        Retrieve details about a specific system component.

        Args:
            component_name (str): Name of the component to retrieve.

        Returns:
            Dict[str, Any]: Component details from the registry.
        """
        return self.registry.get('components', {}).get(component_name, {})
