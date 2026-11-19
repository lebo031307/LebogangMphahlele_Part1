package lebogangmphahlele_part1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * StoredMessages keeps every message that QuickChat handles in parallel arrays
 * (PROG5121 POE Part 3).
 *
 * "Parallel" means that the same index in every array belongs to the same
 * message. For example, messageIDs.get(2), messageHashes.get(2),
 * messageRecipients.get(2), messageTexts.get(2) and messageFlags.get(2) all
 * describe the third message in the list.
 *
 * The main arrays hold one entry for every message:
 * messageIDs, messageHashes, messageRecipients, messageTexts and
 * messageFlags (the flag is "Sent", "Stored" or "Disregard").
 *
 * The three category arrays sentMessages, disregardedMessages and
 * storedMessages hold only the message texts. They are rebuilt from the main
 * arrays every time something changes, so they always match the main arrays.
 *
 * Stored messages are saved in a JSON file and are read back from that file
 * into the arrays.
 *
 * References :
 * - Farrell, J. Java Programming (prescribed textbook): arrays, ArrayList,
 *   loops and strings.
 * - W3Schools. Java ArrayList. https://www.w3schools.com/java/java_arraylist.asp
 * - W3Schools. Java Create and Write To Files.
 *   https://www.w3schools.com/java/java_files_create.asp
 * - W3Schools. Java Read Files. https://www.w3schools.com/java/java_files_read.asp
 * - json-simple library (json-simple-1.1.jar), used to read the JSON file.
 *   https://github.com/fangyidong/json-simple
 */
public class StoredMessages {

    // The three flags (labels) that say what happened to a message.
    private static final String SENT = "Sent";
    private static final String STORED = "Stored";
    private static final String DISREGARDED = "Disregard";

    // The name of the JSON file. The unit tests change this name so that they
    // never touch the real file.
    private static String jsonFileName = "messages.json";

    // Main parallel arrays: one entry for every message that was handled.
    private static ArrayList<String> messageIDs = new ArrayList<String>();
    private static ArrayList<String> messageHashes = new ArrayList<String>();
    private static ArrayList<String> messageRecipients = new ArrayList<String>();
    private static ArrayList<String> messageTexts = new ArrayList<String>();
    private static ArrayList<String> messageFlags = new ArrayList<String>();

    // Category arrays: only the message texts, rebuilt from the main arrays.
    private static ArrayList<String> sentMessages = new ArrayList<String>();
    private static ArrayList<String> disregardedMessages = new ArrayList<String>();
    private static ArrayList<String> storedMessages = new ArrayList<String>();

    // ------------------------------------------------------------------
    // Adding messages to the arrays
    // ------------------------------------------------------------------

    /**
     * Adds a message that the user chose to send.
     *
     * @param id        the ten-digit message ID
     * @param hash      the message hash
     * @param recipient the recipient's cell number
     * @param text      the message text
     */
    public static void addSentMessage(String id, String hash,
            String recipient, String text) {

        addToMainArrays(SENT, id, hash, recipient, text);
        rebuildCategoryArrays();
    }

    /**
     * Adds a message that the user chose to disregard.
     *
     * @param id        the ten-digit message ID
     * @param hash      the message hash
     * @param recipient the recipient's cell number
     * @param text      the message text
     */
    public static void addDisregardedMessage(String id, String hash,
            String recipient, String text) {

        addToMainArrays(DISREGARDED, id, hash, recipient, text);
        rebuildCategoryArrays();
    }

    /**
     * Adds a message to the arrays and then saves all stored messages to the
     * JSON file. If the file cannot be written, the message is removed again
     * so the arrays and the file stay the same.
     *
     * @param id        the ten-digit message ID
     * @param hash      the message hash
     * @param recipient the recipient's cell number
     * @param text      the message text
     * @return true if the message was saved to the JSON file, otherwise false
     */
    public static boolean saveStoredMessage(String id, String hash,
            String recipient, String text) {

        addToMainArrays(STORED, id, hash, recipient, text);
        rebuildCategoryArrays();

        boolean saved = writeStoredMessages();

        if (!saved) {
            // The new message is always the last entry in the main arrays.
            removeEntry(messageIDs.size() - 1);
        }

        return saved;
    }

    // Adds one message to every main array. The category arrays are NOT
    // rebuilt here, so the caller must call rebuildCategoryArrays() afterwards.
    private static void addToMainArrays(String flag, String id, String hash,
            String recipient, String text) {

        messageIDs.add(id);
        messageHashes.add(hash);
        messageRecipients.add(recipient);
        messageTexts.add(text);
        messageFlags.add(flag);
    }

    // Removes the message at the given index from every main array and then
    // rebuilds the category arrays.
    private static void removeEntry(int index) {

        messageIDs.remove(index);
        messageHashes.remove(index);
        messageRecipients.remove(index);
        messageTexts.remove(index);
        messageFlags.remove(index);

        rebuildCategoryArrays();
    }

    // Clears the three category arrays and fills them again from the main
    // arrays, using the flag to decide which category each text belongs to.
    private static void rebuildCategoryArrays() {

        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();

        for (int i = 0; i < messageFlags.size(); i++) {
            String flag = messageFlags.get(i);

            if (flag.equals(SENT)) {
                sentMessages.add(messageTexts.get(i));
            } else if (flag.equals(DISREGARDED)) {
                disregardedMessages.add(messageTexts.get(i));
            } else if (flag.equals(STORED)) {
                storedMessages.add(messageTexts.get(i));
            }
        }
    }

    // ------------------------------------------------------------------
    // Getting copies of the arrays
    // ------------------------------------------------------------------

    /**
     * @return a copy of the array with the texts of all sent messages
     */
    public static ArrayList<String> getSentMessages() {
        return new ArrayList<String>(sentMessages);
    }

    /**
     * @return a copy of the array with the texts of all disregarded messages
     */
    public static ArrayList<String> getDisregardedMessages() {
        return new ArrayList<String>(disregardedMessages);
    }

    /**
     * @return a copy of the array with the texts of all stored messages
     */
    public static ArrayList<String> getStoredMessages() {
        return new ArrayList<String>(storedMessages);
    }

    /**
     * @return a copy of the array with the message IDs of all messages
     */
    public static ArrayList<String> getMessageIDs() {
        return new ArrayList<String>(messageIDs);
    }

    /**
     * @return a copy of the array with the message hashes of all messages
     */
    public static ArrayList<String> getMessageHashes() {
        return new ArrayList<String>(messageHashes);
    }

    // ------------------------------------------------------------------
    // Searching, longest message, delete and reports
    // ------------------------------------------------------------------

    // Looks for a value in a list and returns its index.
    // Returns -1 if the value is not in the list.
    private static int findIndex(ArrayList<String> list, String value) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(value)) {
                return i;
            }
        }

        return -1;
    }

    // Looks for a stored message that has the given hash and returns its
    // index. Sent and disregarded messages are ignored. Returns -1 if no
    // stored message has the hash.
    private static int findStoredIndexByHash(String hash) {

        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageFlags.get(i).equals(STORED)
                    && messageHashes.get(i).equals(hash)) {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Searches all messages (sent, stored and disregarded) for a message ID.
     *
     * @param id the message ID to look for
     * @return the message text, or null if the ID was not found
     */
    public static String searchByMessageID(String id) {

        int index = findIndex(messageIDs, id);

        if (index == -1) {
            return null;
        }

        return messageTexts.get(index);
    }

    /**
     * Searches all messages (sent, stored and disregarded) for a message ID.
     *
     * @param id the message ID to look for
     * @return the recipient of that message, or null if the ID was not found
     */
    public static String getRecipientByMessageID(String id) {

        int index = findIndex(messageIDs, id);

        if (index == -1) {
            return null;
        }

        return messageRecipients.get(index);
    }

    /**
     * Finds the texts of all sent or stored messages for one recipient.
     * Disregarded messages are not included.
     *
     * @param recipient the recipient's cell number
     * @return the message texts in the order they were added (empty if none)
     */
    public static ArrayList<String> searchByRecipient(String recipient) {

        ArrayList<String> found = new ArrayList<String>();

        for (int i = 0; i < messageRecipients.size(); i++) {
            boolean sameRecipient = messageRecipients.get(i).equals(recipient);
            boolean notDisregarded = !messageFlags.get(i).equals(DISREGARDED);

            if (sameRecipient && notDisregarded) {
                found.add(messageTexts.get(i));
            }
        }

        return found;
    }

    /**
     * Finds the longest stored message. If two messages are equally long,
     * the first one that was added is returned.
     *
     * @return the longest stored message, or null if there are no stored messages
     */
    public static String getLongestStoredMessage() {

        if (storedMessages.isEmpty()) {
            return null;
        }

        String longest = storedMessages.get(0);

        for (int i = 1; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).length() > longest.length()) {
                longest = storedMessages.get(i);
            }
        }

        return longest;
    }

    /**
     * Builds one line for every stored message that shows who sent it and who
     * it was sent to.
     *
     * @param sender the name of the logged-in user
     * @return the lines in the order the messages were added (empty if none)
     */
    public static ArrayList<String> getSendersAndRecipients(String sender) {

        ArrayList<String> lines = new ArrayList<String>();

        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(STORED)) {
                lines.add("Sender: " + sender
                        + " | Recipient: " + messageRecipients.get(i));
            }
        }

        return lines;
    }

    /**
     * Deletes the stored message that has the given hash from the arrays and
     * from the JSON file. Sent and disregarded messages cannot be deleted
     * this way. If two stored messages have the same hash, only the first one
     * is deleted.
     *
     * @param hash the message hash of the stored message to delete
     * @return a sentence that says what happened
     */
    public static String deleteMessageByHash(String hash) {

        int index = findStoredIndexByHash(hash);

        if (index == -1) {
            return "No stored message has that hash.";
        }

        String deletedText = messageTexts.get(index);

        removeEntry(index);

        if (!writeStoredMessages()) {
            return "The message was deleted, but the JSON file could not be updated.";
        }

        // The POE example shows the deleted message without its final full
        // stop, so the final full stop is left out of the sentence.
        String shownText = deletedText;
        if (shownText.endsWith(".")) {
            shownText = shownText.substring(0, shownText.length() - 1);
        }

        return "Message: \"" + shownText + "\" successfully deleted.";
    }

    /**
     * @return a report with the full details of all sent messages
     */
    public static String getSentReport() {
        return createReport(SENT, "No messages have been sent yet.");
    }

    /**
     * @return a report with the full details of all stored messages
     */
    public static String getStoredReport() {
        return createReport(STORED, "There are no stored messages.");
    }

    // Builds a report for the messages that have the given flag. Each message
    // shows its ID, hash, recipient and text. If no message has the flag, the
    // emptyText is returned instead.
    private static String createReport(String flag, String emptyText) {

        String report = "";

        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(flag)) {
                report = report + "Message ID: " + messageIDs.get(i) + "\n"
                        + "Message Hash: " + messageHashes.get(i) + "\n"
                        + "Recipient: " + messageRecipients.get(i) + "\n"
                        + "Message: " + messageTexts.get(i) + "\n\n";
            }
        }

        if (report.equals("")) {
            return emptyText;
        }

        return report;
    }

    // ------------------------------------------------------------------
    // Reading and writing the JSON file
    // ------------------------------------------------------------------

    /**
     * Reads the JSON file into the arrays. The stored messages that are
     * already in the arrays are replaced by the messages in the file. Sent and
     * disregarded messages are not changed.
     *
     * Reading adapted from the json-simple library
     * (https://github.com/fangyidong/json-simple) and from W3Schools
     * "Java Read Files" (https://www.w3schools.com/java/java_files_read.asp).
     *
     * @return true if the file was read, false if it does not exist or cannot
     *         be read (the arrays are not changed in that case)
     */
    public static boolean loadStoredMessages() {

        File jsonFile = new File(jsonFileName);

        if (!jsonFile.exists()) {
            return false;
        }

        // The try-with-resources line closes the file automatically.
        try (FileReader reader = new FileReader(jsonFile)) {

            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(reader);

            // The file must contain a list ([ ... ]) of messages.
            if (!(parsed instanceof JSONArray)) {
                System.out.println("The JSON file does not contain a list of messages.");
                return false;
            }

            JSONArray storedList = (JSONArray) parsed;

            // Remove the old stored messages. The file replaces them.
            removeStoredEntries();

            for (int i = 0; i < storedList.size(); i++) {
                JSONObject item = (JSONObject) storedList.get(i);

                addToMainArrays(STORED,
                        readField(item, "messageID"),
                        readField(item, "messageHash"),
                        readField(item, "recipient"),
                        readField(item, "message"));
            }

            rebuildCategoryArrays();
            return true;

        } catch (IOException e) {
            System.out.println("The JSON file could not be read.");
            return false;
        } catch (ParseException e) {
            System.out.println("The JSON file is not in the correct format.");
            return false;
        }
    }

    // Gets one field from a JSON object as text. A missing field gives "".
    private static String readField(JSONObject item, String key) {

        Object value = item.get(key);

        if (value == null) {
            return "";
        }

        return value.toString();
    }

    // Removes every message that has the "Stored" flag from the arrays.
    // The loop starts at the end so that removing an entry does not move the
    // entries that have not been checked yet.
    private static void removeStoredEntries() {

        for (int i = messageFlags.size() - 1; i >= 0; i--) {
            if (messageFlags.get(i).equals(STORED)) {
                removeEntry(i);
            }
        }
    }

    // Writes every message that has the "Stored" flag to the JSON file. The
    // old content of the file is replaced.
    // File writing adapted from: W3Schools "Java Create and Write To Files"
    // (https://www.w3schools.com/java/java_files_create.asp).
    // The JSON layout follows the format described at https://www.json.org
    private static boolean writeStoredMessages() {

        try (FileWriter writer = new FileWriter(jsonFileName)) {

            writer.write("[\n");

            boolean firstEntry = true;

            for (int i = 0; i < messageFlags.size(); i++) {
                if (messageFlags.get(i).equals(STORED)) {

                    // A comma goes between the entries, but not before the first one.
                    if (!firstEntry) {
                        writer.write(",\n");
                    }
                    firstEntry = false;

                    writer.write("  {\n");
                    writer.write("    \"messageID\": \""
                            + escapeJson(messageIDs.get(i)) + "\",\n");
                    writer.write("    \"messageHash\": \""
                            + escapeJson(messageHashes.get(i)) + "\",\n");
                    writer.write("    \"recipient\": \""
                            + escapeJson(messageRecipients.get(i)) + "\",\n");
                    writer.write("    \"message\": \""
                            + escapeJson(messageTexts.get(i)) + "\"\n");
                    writer.write("  }");
                }
            }

            writer.write("\n]\n");
            return true;

        } catch (IOException e) {
            System.out.println("Error writing to the JSON file.");
            return false;
        }
    }

    // Escapes characters that could break the JSON format.
    private static String escapeJson(String text) {

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // ------------------------------------------------------------------
    // Helpers for the unit tests
    // ------------------------------------------------------------------

    /**
     * @return the name of the JSON file that is currently used
     */
    public static String getJsonFileName() {
        return jsonFileName;
    }

    /**
     * Empties all arrays, switches to a separate JSON file for testing and
     * deletes that file. This is only used by the unit tests, so the real
     * messages.json file is never changed by a test.
     */
    public static void resetForTesting() {

        messageIDs.clear();
        messageHashes.clear();
        messageRecipients.clear();
        messageTexts.clear();
        messageFlags.clear();
        rebuildCategoryArrays();

        jsonFileName = "messages_test.json";
        new File(jsonFileName).delete();
    }
}
