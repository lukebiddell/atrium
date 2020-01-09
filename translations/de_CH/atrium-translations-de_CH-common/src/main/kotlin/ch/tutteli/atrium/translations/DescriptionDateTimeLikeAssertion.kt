package ch.tutteli.atrium.translations

import ch.tutteli.atrium.assertions.DescriptiveAssertion
import ch.tutteli.atrium.reporting.translating.StringBasedTranslatable

/**
 * Contains the [DescriptiveAssertion.description]s of the assertion functions
 * which are applicable to date like instances (e.g. LocalDate, LocaleDateTime, ZonedDateTime etc.)
 */
enum class DescriptionDateTimeLikeAssertion(override val value: String) : StringBasedTranslatable {
    YEAR("Jahr"),
    MONTH("Monat"),
    DAY_OF_WEEK("Wochentag"),
<<<<<<< HEAD
    IS_AFTER_OR_EQUALS("ist nach oder gleichzeitig"),
    IS_AFTER("ist nach"),
    IS_BEFORE_OR_EQUALS("ist vor oder gleichzeitig"),
    IS_BEFORE("ist vor"),
    DAY("Tag")
=======
    DAY("Tag"),
    IS_AFTER_OR_EQUALS("ist nach dem oder gleich"),
>>>>>>> rename mentions of equal to equals
}
