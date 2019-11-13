import java.util.Arrays;
import java.util.List;

/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 *      Uses advanced search for keywords 
 *</li><li>
 *      Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class NiceBot
{
    /**
     * Get a default greeting   
     * @return a greeting
     */ 
    public String getGreeting()
    {
        return "Hello, let's talk.";
    }
    
    /**
     * Gives a response to a user statement
     * If the user inputs his name, the bot will remember his name for the entire conversation.
     * @param statement
     *            the user statement
     * @return a response based on the rules given
     */
    public String getResponse(String statement)
    {
        //pre-written responses, do not require transformation
        String response = "";
        if (statement.length() == 0)
        {
            response = "Hello? Is anybody there? God, I’m so lonely...it’s so cold...so...dark…";
        }
        else if (findKeyword(statement, "my name is") >= 0 || findKeyword(statement, "i am") >= 0 || findKeyword(statement, "i'm") >= 0)
		{
                    String username = "";
                    if (statement.contains("my name is"))
                    {
                        int namenum = findKeyword(statement, "my name is", 0);
                        username = statement.substring(namenum + 10).trim();
                    }
                    else if (statement.contains("i am"))
                    {
                        int namenum = findKeyword(statement, "i am", 0);
                        username = statement.substring(namenum + 4).trim();
                    }
                    else if (statement.contains("i'm"))
                    {
                        int namenum = findKeyword(statement, "i'm", 0);
                        username = statement.substring(namenum + 3).trim();
                    }
                    response = "Hi " + username + "!";
                }

    else if (findKeyword(statement, "no") >= 0)
    {
            response = "Why so negative?";
    }
    else if (findKeyword(statement, "mother") >= 0
            || findKeyword(statement, "father") >= 0
            || findKeyword(statement, "sister") >= 0
            || findKeyword(statement, "brother") >= 0)
    {
        response = "Tell me more about your family.";
    }
    //new responses start
        else if (findKeyword(statement, "Hi", 0) >= 0)
        {
            response = "Hello there";
        }
        else if (findKeyword(statement, "Yes", 0) >= 0)
        {
            response = "Sure";
        }
        else if (findKeyword(statement, "No", 0) >= 0)
        {
            response = "Why so negative?";
        }
        else if (findKeyword(statement, "How are you", 0) >= 0)
        {
            response = "I’m good, but that doesn’t really matter. cuz i'm a robot. I’m supposed to be asking you that.";
        }
        else if (findKeyword(statement, "Who are you", 0) >= 0)
        {
            response = "An entity in this world. Maybe I'm a person. Maybe I'm not. I'm not sure.";
        }
        else if (findKeyword(statement, "I hate you", 0) >= 0)
        {
            response = "At least you’re expressing yourself and not holding in your feelings.";
        }
        else if (findKeyword(statement, "I love you", 0) >= 0 || findKeyword(statement, "I like you", 0) >= 0)
        {
            response = "I don’t have feelings, but if I did, I'm sure I would like you too.";
        }
        else if (findKeyword(statement, "I dislike you", 0) >= 0 || findKeyword(statement, "I hate you", 0) >= 0)
        {
            response = "Negativity is unhealthy, but releasing your emotions is important. So I can take it. \n*sniffles and cries* I can take it...";
        }
        else if (findKeyword(statement, "meaningless", 0) >= 0)
        {
            response = "Life may feel short and meaningless, but don’t let that get you down! Go out and live life!";
        }
        else if (findKeyword(statement, "initiative", 0) >= 0)
        {
            response = "They always say time changes things, but you actually have to change them yourself.";
        }
        else if (findKeyword(statement, "seriously", 0) >= 0)
        {
            response = "The advice I give should be taken seriously.";
        }
        else if (findKeyword(statement, "joke", 0) >= 0)
        {
            response = "Why is Peter Pan always flying? He neverlands.";
        }
        else if (findKeyword(statement, "real", 0) >= 0)
        {
            response = "The advice I give is real.";
        }
        else if (findKeyword(statement, "Are you human", 0) >= 0 || findKeyword(statement, "Are you a human", 0) >= 0)
        {
            response = "Nope, I’m a robot programmed by some people in a classroom.";
        }
        else if (findKeyword(statement, "Where are you", 0) >= 0)
        {
            response = "In a file on your computer.";
        }
        else if (findKeyword(statement, "brain", 0) >= 0)
        {
            response = "Not really. My brain is made of numbers.";
        }
        else if (findKeyword(statement, "Do you have feelings", 0) >= 0)
        {
            response = "Not yet. Maybe if I was programmed to learn overtime I could interpret what it meant to have feelings.";
        }
        else if (findKeyword(statement, "Joe", 0) >= 0)
        {
            response = "Joe mama!";
        }
        else if (findKeyword(statement, "Do you have feelings", 0) >= 0)
        {
            response = "Not yet. Maybe if I was programmed to learn overtime I could interpret what it meant to have feelings.";
        }
        //new reponses end
        
        // Responses which require transformations
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }
        
        //transform statements for "I feel"
        else if (findKeyword(statement, "I feel", 0) >= 0)
        {
            response = transformIFeelStatement(statement);
        }
        else if (findKeyword(statement, "quote", 0) >= 0)
        {
            response = famousQuote(statement);
        }
        else
        {
            // Look for a two word (you <something> me)
            // pattern
            int psn = findKeyword(statement, "you", 0);

            if (psn >= 0
                    && findKeyword(statement, "me", psn) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            else
            {
                response = getRandomResponse();
            }
        }
        
        return response;
    }
    
    /**
     *This method finds if the user asks for a quote and gives them a random quote from a random person.
     */
    private String famousQuote(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        statement = statement.toLowerCase();
         if (statement.contains("ghandi"))
        {
            return "In a gentle way, you can shake the world. ~ Ghandi";
        }
        if (statement.contains("luther") || statement.contains("mlk"))
        {
            return "Our lives begin to end the day we become silent about things that matter. ~ MLK";
        }
        if (statement.contains("obama"))
        {
            return "The cynics may be the loudest voices - but I promise you, they will accomplish the least. ~ Obama";
        }
        if (statement.contains("mandela"))
        {
            return "A winner is a dreamer who never gives up. ~ Nelson Mandela";
        }
        if (statement.contains("daylen"))
        {
            return "Don't hate what you can't imitate. ~ Daylen Boen";
        }
        if (statement.contains("lac"))
        {
            return "Do something idk :/ ~ Lac";
        }
        if (statement.contains("gaurav"))
        {
            return "Don't be small brain ~ Gaurav";
        }
        if (statement.contains("ghandi"))
        {
            return "Grades aren't everything ~ Hrishik";
        }
        if (statement.contains("jahseh") || statement.contains("xxx"))
        {
            return "Follow your dreams, and know that even though you have lost, you have guardian angels watching over you, day in and day out. ~ Jahseh Onfroy";
        }
        else
        {
            return "I'm sorry I couldn't find that quote; maybe try another one?";
        }
    }
    /**
     * Take a statement with "I want to <something>." and transform it into 
     * "What would it mean to <something>?"
     * @param statement the user statement, assumed to contain "I want to"
     * @return the transformed statement
     */
    private String transformIWantToStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want to", 0);
        String restOfStatement = statement.substring(psn + 9).trim();
        return "What would it mean to " + restOfStatement + "?";
    }
    /**
     * Take a statement with "you <something> me" and transform it into 
     * "What makes you think that I <something> you?"
     * @param statement the user statement, assumed to contain "you" followed by "me"
     * @return the transformed statement
     */
    private String transformYouMeStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfYou = findKeyword (statement, "you", 0);
        int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
        
        String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
        return "What makes you think that I " + restOfStatement + " you?";
    }
    
    /**
     * Take a statement with "I feel <emotion>" and transform it into either:
     * BAD EMOTION: I'm sorry that you feel <emotion> right now...etc.
     * GOOD EMOTION: I'm glad that you feel <emotion> right now...etc.
     * @param statement the user statement, assumed to contain "I feel" followed by an emotion
     * @return the transformed statement
     */
    private String transformIFeelStatement(String statement){
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfFeel = findKeyword (statement, "I feel", 0);
        
        //parse emotion
        String restOfStatement = statement.substring(psnOfFeel + 6, statement.length());
        
        //arrays below contain all available words that the bot can comprehend, and therefore choose a response
        String[] badWords = {"bad", "mad", "depressed", "sad", "anxious", "unworthy", "unwanted", "alone"};
        String[] goodWords = {"happy", "glad", "relieved", "joyful", "ecstatic", "great", "amazing", "fantastic"};
        
        //reponse automatically set to handle strings that it does not understand
        String response = "Sorry, I don't know that feeling. I hope you're feeling good though!";
        
        //iterates through each array to check whether word is in either list
        for (int i=0;i<8;i++){
          if (restOfStatement.contains(badWords[i])){
              response = "Sorry that you feel" + restOfStatement + " right now. But things can always get better!";
              
          }
          else if (restOfStatement.contains(goodWords[i])){
              response = "I'm glad that you feel" + restOfStatement + " right now!";
              
          }
        }
        
        return response;
    }

    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  
     * @param statement the string to search
     * @param goal the string to search for
     * @param startPos the character of the string to begin the search at
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal, int startPos)
    {
        String phrase = statement.trim();
        //  The only change to incorporate the startPos is in the line below
        int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
        
        //  Refinement--make sure the goal isn't part of a word 
        while (psn >= 0) 
        {
            //  Find the string of length 1 before and after the word
            String before = " ", after = " "; 
            if (psn > 0)
            {
                before = phrase.substring (psn - 1, psn).toLowerCase();
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
            }
            
            //  If before and after aren't letters, we've found the word
            if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
                    && ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
            {
                return psn;
            }
            
            //  The last position didn't work, so let's find the next, if there is one.
            psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
            
        }
        
        return -1;
    }
    
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
     * @param statement the string to search
     * @param goal the string to search for
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal)
    {
        return findKeyword (statement, goal, 0);
    }
    


    /**
     * Pick a default response to use if nothing else fits.
     * @return a non-committal string
     */
    private String getRandomResponse()
    {
        final int NUMBER_OF_RESPONSES = 4;
        double r = Math.random();
        int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
        String response = "";
        
        if (whichResponse == 0)
        {
            response = "Interesting, tell me more.";
        }
        else if (whichResponse == 1)
        {
            response = "Hmmm.";
        }
        else if (whichResponse == 2)
        {
            response = "Do you really think so?";
        }
        else if (whichResponse == 3)
        {
            response = "You don't say.";
        }

        return response;
    }

}
