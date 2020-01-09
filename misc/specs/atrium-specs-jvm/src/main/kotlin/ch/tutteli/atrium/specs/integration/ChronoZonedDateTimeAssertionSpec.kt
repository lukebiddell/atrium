package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.fluent.en_GB.messageContains
import ch.tutteli.atrium.api.fluent.en_GB.toThrow
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.translations.DescriptionDateTimeLikeAssertion
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime

abstract class ChronoZonedDateTimeAssertionSpec(
    isBefore: Fun1<ChronoZonedDateTime<*>, ChronoZonedDateTime<*>>,
    isBeforeOrEquals: Fun1<ChronoZonedDateTime<*>, ChronoZonedDateTime<*>>,
    isAfter: Fun1<ChronoZonedDateTime<*>, ChronoZonedDateTime<*>>,
    isAfterOrEquals: Fun1<ChronoZonedDateTime<*>, ChronoZonedDateTime<*>>,
    describePrefix: String = "[Atrium] "
) : Spek({

    val ten = ZonedDateTime.of(2019, 12, 24, 10, 15, 30, 210, ZoneId.of("Europe/Zurich"))
    val eleven = ZonedDateTime.of(2019, 12, 24, 11, 15, 30, 210, ZoneId.of("Europe/Zurich"))
    val twelve = ZonedDateTime.of(2019, 12, 24, 12, 15, 30, 210, ZoneId.of("Europe/Zurich"))

    include(object : SubjectLessSpec<ChronoZonedDateTime<*>>(
        describePrefix,
        isBefore.forSubjectLess(eleven),
        isBeforeOrEquals.forSubjectLess(eleven),
        isAfter.forSubjectLess(eleven),
        isAfterOrEquals.forSubjectLess(eleven)
    ) {})

    val isBeforeDescr = DescriptionDateTimeLikeAssertion.IS_BEFORE.getDefault()
    val isBeforeOrEqualsDescr = DescriptionDateTimeLikeAssertion.IS_BEFORE_OR_EQUALS.getDefault()
    val isAfterDescr = DescriptionDateTimeLikeAssertion.IS_AFTER.getDefault()
    val isAfterOrEqualsDescr = DescriptionDateTimeLikeAssertion.IS_AFTER_OR_EQUALS.getDefault()


    listOf<ChronoZonedDateTime<*>>(
        eleven,
        eleven.withZoneSameInstant(ZoneOffset.UTC)
    ).forEach { subject ->

        val fluent = expect(subject)

        describe("$describePrefix subject is $subject") {
            describe("${isBefore.name} ...") {
                val isBeforeFun = isBefore.lambda

                it("$ten throws an AssertionError") {
                    expect {
                        fluent.isBeforeFun(ten)
                    }.toThrow<AssertionError> { messageContains("$isBeforeDescr: $ten") }
                }
                it("$eleven does not throw") {
                    expect {
                        fluent.isBeforeFun(eleven)
                    }.toThrow<AssertionError> { messageContains("$isBeforeDescr: $eleven") }
                }
                it("$twelve does not throw") {
                    fluent.isBeforeFun(twelve)
                }
            }
            describe("${isBeforeOrEquals.name} ...") {
                val isBeforeOrEqualsFun = isBeforeOrEquals.lambda

                it("$ten throws an AssertionError") {
                    expect {
                        fluent.isBeforeOrEqualsFun(ten)
                    }.toThrow<AssertionError> { messageContains("$isBeforeOrEqualsDescr: $ten") }
                }
                it("$eleven does not throw") {
                    fluent.isBeforeOrEqualsFun(eleven)
                }
                it("$twelve does not throw") {
                    fluent.isBeforeOrEqualsFun(twelve)
                }
            }
            describe("${isAfter.name} ...") {
                val isAfterFun = isAfter.lambda

                it("$ten does not throw") {
                    fluent.isAfterFun(ten)
                }
                it("$eleven throws an AssertionError") {
                    expect {
                        fluent.isAfterFun(eleven)
                    }.toThrow<AssertionError> { messageContains("$isAfterDescr: $eleven") }
                }
                it("$twelve throws an AssertionError") {
                    expect {
                        fluent.isAfterFun(twelve)
                    }.toThrow<AssertionError> { messageContains("$isAfterDescr: $twelve") }
                }
            }
            describe("${isAfterOrEquals.name} ...") {
                val isAfterOrEqualsFun = isAfterOrEquals.lambda

                it("$ten does not throw") {
                    fluent.isAfterOrEqualsFun(ten)
                }
                it("$eleven does not throw") {
                    fluent.isAfterOrEqualsFun(eleven)
                }
                it("$twelve throws an AssertionError") {
                    expect {
                        fluent.isAfterOrEqualsFun(twelve)
                    }.toThrow<AssertionError> { messageContains("$isAfterOrEqualsDescr: $twelve") }
                }
            }
        }
    }
})
