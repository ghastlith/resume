package ghastlith.resume.generation.i18n;

import static java.util.Locale.US;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * I18n message resolver to provide internationalization support for generated
 * resume key messages.
 * <p>
 * Currently supported Locales can be found on {@code resources/i18n/}
 * <p>
 * Currently supported i18n keys can be found on {@link MessageKey}
 */
public class MessageResolver {

  private static final String PROPERTIES_PATH = "i18n/messages";

  private final ResourceBundle bundle;

  public MessageResolver(final Locale locale) {
    this.bundle = loadBundle(locale);
  }

  /**
   * Retrieves the specific message based on bundled locale for desired i18n key.
   * Fallbacks to the message key parameter when string is not present on locale
   * bundle.
   *
   * @param key the identyfing key for the localized string value
   * @return The locale specific message or key itself as fallback.
   */
  public String get(final MessageKey key) {
    final var i18nKey = key.getI18nKey();

    try {
      return bundle.getString(i18nKey);
    } catch (MissingResourceException e) {
      return i18nKey;
    }
  }

  private static ResourceBundle loadBundle(final Locale locale) {
    try {
      return ResourceBundle.getBundle(PROPERTIES_PATH, locale);
    } catch (MissingResourceException e) {
      return ResourceBundle.getBundle(PROPERTIES_PATH, US);
    }
  }

}
