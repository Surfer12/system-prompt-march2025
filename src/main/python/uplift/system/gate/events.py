#!/usr/bin/env python3
"""
Gate Events: Event Management and Handling

This module manages events and interactions within the technological gate,
providing a flexible event-driven architecture.
"""

from typing import Any, Callable, Dict, List
from enum import Enum, auto
from dataclasses import dataclass, field
from datetime import datetime

class EventType(Enum):
    """
    Enumeration of possible event types within the gate.
    """
    TECHNOLOGY_CONNECTION = auto()
    DATA_TRANSFER = auto()
    SYSTEM_NOTIFICATION = auto()
    ERROR = auto()

@dataclass
class Event:
    """
    Represents an event within the system.
    """
    type: EventType
    timestamp: datetime = field(default_factory=datetime.now)
    source: str = ""
    destination: str = ""
    payload: Dict[str, Any] = field(default_factory=dict)
    metadata: Dict[str, Any] = field(default_factory=dict)

class EventManager:
    """
    Manages event registration, dispatching, and handling.
    """
    def __init__(self):
        """
        Initialize the EventManager with empty event handlers.
        """
        self._event_handlers: Dict[EventType, List[Callable[[Event], None]]] = {}
    
    def register_handler(self, event_type: EventType, handler: Callable[[Event], None]) -> None:
        """
        Register an event handler for a specific event type.
        
        Args:
            event_type (EventType): Type of event to handle.
            handler (Callable[[Event], None]): Function to handle the event.
        """
        if event_type not in self._event_handlers:
            self._event_handlers[event_type] = []
        self._event_handlers[event_type].append(handler)
    
    def dispatch_event(self, event: Event) -> None:
        """
        Dispatch an event to all registered handlers.
        
        Args:
            event (Event): Event to dispatch.
        """
        if event.type in self._event_handlers:
            for handler in self._event_handlers[event.type]:
                handler(event)
    
    def create_technology_connection_event(self, source: str, destination: str, payload: Dict[str, Any] = None) -> Event:
        """
        Create a technology connection event.
        
        Args:
            source (str): Source technology.
            destination (str): Destination technology.
            payload (Dict[str, Any], optional): Additional event data.
        
        Returns:
            Event: Created technology connection event.
        """
        return Event(
            type=EventType.TECHNOLOGY_CONNECTION,
            source=source,
            destination=destination,
            payload=payload or {}
        )
    
    def create_data_transfer_event(self, source: str, destination: str, data: Any) -> Event:
        """
        Create a data transfer event.
        
        Args:
            source (str): Source of the data.
            destination (str): Destination for the data.
            data (Any): Data being transferred.
        
        Returns:
            Event: Created data transfer event.
        """
        return Event(
            type=EventType.DATA_TRANSFER,
            source=source,
            destination=destination,
            payload={"data": data}
        )

def create_default_event_manager() -> EventManager:
    """
    Create a default EventManager with some basic logging handlers.
    
    Returns:
        EventManager: Configured event manager with logging handlers.
    """
    event_manager = EventManager()
    
    def log_technology_connection(event: Event) -> None:
        """
        Log technology connection events.
        
        Args:
            event (Event): Technology connection event.
        """
        print(f"Technology Connection: {event.source} → {event.destination}")
    
    def log_data_transfer(event: Event) -> None:
        """
        Log data transfer events.
        
        Args:
            event (Event): Data transfer event.
        """
        print(f"Data Transfer: {event.source} → {event.destination}")
    
    event_manager.register_handler(EventType.TECHNOLOGY_CONNECTION, log_technology_connection)
    event_manager.register_handler(EventType.DATA_TRANSFER, log_data_transfer)
    
    return event_manager 