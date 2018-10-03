class PrioritySort implements Comparator<Event> {
    // Sort events in ascending order of priority.
    
    public int compare(Event a, Event b) {
        return a.getEventPriority() - b.getEventPriority();
    }
}