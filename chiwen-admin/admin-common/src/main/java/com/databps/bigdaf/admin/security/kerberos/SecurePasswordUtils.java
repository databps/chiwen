package com.databps.bigdaf.admin.security.kerberos;



import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SecurePasswordUtils {

  /**
   * The default number of characters to generate for a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_LENGTH = 18;
  /**
   * The default minimum number of lowercase letters to include when generating a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_MIN_LOWERCASE_LETTERS = 1;
  /**
   * The default minimum number of uppercase letters to include when generating a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_MIN_UPPERCASE_LETTERS = 1;
  /**
   * The default minimum number of digits to include when generating a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_MIN_DIGITS = 1;
  /**
   * The default minimum number of punctuation characters to include when generating a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_MIN_PUNCTUATION = 1;
  /**
   * The default minimum number of whitespace characters to include when generating a secure password
   */
  public final static int DEFAULT_SECURE_PASSWORD_MIN_WHITESPACE = 1;

  /**
   * The set of available lowercase letters to use when generating a secure password
   */
  protected final static char[] SECURE_PASSWORD_CHARACTER_CLASS_LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  /**
   * The set of available uppercase letters to use when generating a secure password
   */
  protected final static char[] SECURE_PASSWORD_CHARACTER_CLASS_UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  /**
   * The set of available digits to use when generating a secure password
   */
  protected final static char[] SECURE_PASSWORD_CHARACTER_CLASS_DIGITS = "0123456789".toCharArray();
  /**
   * The set of available punctuation characters to use when generating a secure password
   */
  protected final static char[] SECURE_PASSWORD_CHARACTER_CLASS_PUNCTUATION = "?.!$%^*()-_+=~".toCharArray();
  /**
   * The set of available whitespace characters to use when generating a secure password
   */
  protected final static char[] SECURE_PASSWORD_CHARACTER_CLASS_WHITESPACE = " ".toCharArray();
  /**
   * The collection of available character classes
   */
  private final static char[][] SECURE_PASSWORD_CHARACTER_CLASSES = {
      SECURE_PASSWORD_CHARACTER_CLASS_LOWERCASE_LETTERS,
      SECURE_PASSWORD_CHARACTER_CLASS_UPPERCASE_LETTERS,
      SECURE_PASSWORD_CHARACTER_CLASS_DIGITS,
      SECURE_PASSWORD_CHARACTER_CLASS_PUNCTUATION,
      SECURE_PASSWORD_CHARACTER_CLASS_WHITESPACE
  };
  
  private static final SecureRandom secureRandom = new SecureRandom();


  public static String createSecurePassword() {
    return createSecurePassword(null, null, null, null, null, null);
  }


  public static String createSecurePassword(Integer length, Integer minLowercaseLetters, Integer minUppercaseLetters, Integer minDigits, Integer minPunctuation, Integer minWhitespace) {
    if ((length == null) || (length < 1)) {
      length = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_LENGTH;
    }

    if (minLowercaseLetters == null) {
      minLowercaseLetters = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_MIN_LOWERCASE_LETTERS;
    }

    if (minUppercaseLetters == null) {
      minUppercaseLetters = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_MIN_UPPERCASE_LETTERS;
    }

    if (minDigits == null) {
      minDigits = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_MIN_DIGITS;
    }

    if (minPunctuation == null) {
      minPunctuation = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_MIN_PUNCTUATION;
    }

    if (minWhitespace == null) {
      minWhitespace = SecurePasswordUtils.DEFAULT_SECURE_PASSWORD_MIN_WHITESPACE;
    }

    // Gather the set of characters that meet the specified requirements
    List<Character> characters = new ArrayList<>(length);

    for (int i = 0; i < minLowercaseLetters; i++) {
      characters.add(SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_LOWERCASE_LETTERS[secureRandom.nextInt(
          SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_LOWERCASE_LETTERS.length)]);
    }

    for (int i = 0; i < minUppercaseLetters; i++) {
      characters.add(SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_UPPERCASE_LETTERS[secureRandom.nextInt(
          SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_UPPERCASE_LETTERS.length)]);
    }

    for (int i = 0; i < minDigits; i++) {
      characters.add(SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_DIGITS[secureRandom.nextInt(
          SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_DIGITS.length)]);
    }

    for (int i = 0; i < minPunctuation; i++) {
      characters.add(SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_PUNCTUATION[secureRandom.nextInt(
          SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_PUNCTUATION.length)]);
    }

    for (int i = 0; i < minWhitespace; i++) {
      characters.add(SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_WHITESPACE[secureRandom.nextInt(
          SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASS_WHITESPACE.length)]);
    }

    // If we need to gather more characters, select randomly from the set of character classes
    if (characters.size() < length) {
      int difference = length - characters.size();
      for (int i = 0; i < difference; i++) {
        char[] characterClass = SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASSES[secureRandom.nextInt(
            SecurePasswordUtils.SECURE_PASSWORD_CHARACTER_CLASSES.length - 1)];
        characters.add(characterClass[secureRandom.nextInt(characterClass.length)]);
      }
    }

    // Generate the password string by randomly selecting from the list of available characters
    StringBuilder passwordBuilder = new StringBuilder(characters.size());

    while (!characters.isEmpty()) {
      passwordBuilder.append(characters.remove(secureRandom.nextInt(characters.size())));
    }

    return passwordBuilder.toString();
  }

  
  public static void main(String args[]) {
	  String password = new SecurePasswordUtils().createSecurePassword();
  }
}
