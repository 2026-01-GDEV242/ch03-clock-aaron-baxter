/**
 * 12 HOUR INTERNAL BRANCH
 * 
 * The ClockDisplay class implements a digital clock display for a
 * American-style 12 hour clock. The clock shows hours and minutes. 
 * 
 * The internal range of the clock is 00:00 (midnight) to 11:59 
 * In order to track AM or PM, we will need additional Fields and logic to 
 * handle the switch in meridian
 * 
 * The clock display receives "ticks" (via the timeTick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the minutes roll over to zero.
 * 
 * @author Aaron Baxter
 * @version 2026.02.25
 */
public class ClockDisplay
{
    private NumberDisplay hours;     // internal range: 0-11 (0 means 12)
    private NumberDisplay minutes;   // internal range: 0-59
    private boolean isPM;            // false = AM, true = PM
    private String displayString;    // simulates the actual display
    
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public ClockDisplay()
    {
        hours = new NumberDisplay(12);
        minutes = new NumberDisplay(60);
        isPM = false; //AM
        updateDisplay();
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     * 
     */
    public ClockDisplay(int hour, int minute)
    {
        hours = new NumberDisplay(12);
        minutes = new NumberDisplay(60);
        setTime(hour, minute);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void timeTick()
    {
        minutes.increment();
        if(minutes.getValue() == 0) {  // it just rolled over!
            hours.increment();
            
            // Rolling from 11 -> 0 means 12, and that transition flips AM <-> PM.
            if(hours.getValue() == 0) {
                isPM = !isPM;
            }
        }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute.
     */
    public void setTime(int hour, int minute)
    {
        // Determine AM/PM first (12-23 is PM, 0-11 is AM)
        isPM = (hour >= 12);
        
        // Convert 24-hour to internal 12-hour representation (0-11)
        int internalHour = hour % 12;
        
        hours.setValue(internalHour);
        minutes.setValue(minute);
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime()
    {
        return displayString;
    }
    
    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay()
    {
        int h = hours.getValue();
        int displayHour = (h == 0) ? 12 : h;
        
        // Force the hour display to be 2 digits (01-12)
        String hourString = (displayHour < 10) ? ("0" + displayHour) : ("" + displayHour);
        
        displayString = hourString + ":" + minutes.getDisplayValue() + 
                        (isPM ? " PM" : " AM");
    }
}
