package ch.tutteli.atrium.specs.integration

import ch.tutteli.atrium.api.fluent.en_GB.isGreaterThan
import ch.tutteli.atrium.api.fluent.en_GB.isLessOrEquals
import ch.tutteli.atrium.api.fluent.en_GB.isLessThan
import ch.tutteli.atrium.api.fluent.en_GB.messageContains
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.fluent.en_GB.toThrow
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.specs.AssertionCreatorSpec
import ch.tutteli.atrium.specs.Feature0
import ch.tutteli.atrium.specs.Fun1
import ch.tutteli.atrium.specs.SubjectLessSpec
import ch.tutteli.atrium.specs.adjustName
import ch.tutteli.atrium.specs.describeFunTemplate
import ch.tutteli.atrium.specs.forAssertionCreatorSpec
import ch.tutteli.atrium.specs.forSubjectLess
import ch.tutteli.atrium.specs.lambda
import ch.tutteli.atrium.specs.name
import ch.tutteli.atrium.specs.toBeDescr
import ch.tutteli.atrium.translations.DescriptionDateTimeLikeAssertion
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.Suite
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.MonthDay
import java.time.ZonedDateTime

abstract class ZonedDateTimeFeatureAssertionsSpec(
    yearFeature: Feature0<ZonedDateTime, Int>,
    year: Fun1<ZonedDateTime, Expect<Int>.() -> Unit>,
    monthFeature: Feature0<ZonedDateTime, Int>,
    month: Fun1<ZonedDateTime, Expect<Int>.() -> Unit>,
    dayFeature: Feature0<ZonedDateTime, Int>,
    day: Fun1<ZonedDateTime, Expect<Int>.() -> Unit>,
    dayOfWeekFeature: Feature0<ZonedDateTime, DayOfWeek>,
    dayOfWeek: Fun1<ZonedDateTime, Expect<DayOfWeek>.() -> Unit>,
    describePrefix: String = "[Atrium] "
) : Spek({

    include(object : SubjectLessSpec<ZonedDateTime>(describePrefix,
        yearFeature.forSubjectLess().adjustName { "$it feature" },
        year.forSubjectLess { isGreaterThan(2000) },
        monthFeature.forSubjectLess().adjustName { "$it feature" },
        month.forSubjectLess { isLessThan(12) },
        dayFeature.forSubjectLess().adjustName { "$it feature" },
        day.forSubjectLess { isLessOrEquals(20) },
        dayOfWeekFeature.forSubjectLess().adjustName { "$it feature" },
        dayOfWeek.forSubjectLess { isLessOrEquals(DayOfWeek.SUNDAY) }
    ) {})

    include(object : AssertionCreatorSpec<ZonedDateTime>(
        describePrefix, ZonedDateTime.now().withYear(2040).withDayOfYear(1).withDayOfMonth(15),
        year.forAssertionCreatorSpec("$toBeDescr: 1") { toBe(2040) },
        month.forAssertionCreatorSpec("$toBeDescr: 1") {toBe(1)},
        day.forAssertionCreatorSpec("$toBeDescr: 1") {toBe(15)},
        dayOfWeek.forAssertionCreatorSpec("$toBeDescr: 1") {toBe(DayOfWeek.SUNDAY)}
    ) {})

    fun describeFun(vararg funName: String, body: Suite.() -> Unit) =
        describeFunTemplate(describePrefix, funName, body = body)

    val fluent = expect(ZonedDateTime.now().withMonth(5).withYear(2009).withDayOfMonth(15))
    val monthDescr = DescriptionDateTimeLikeAssertion.MONTH.getDefault()
    val yearDescr = DescriptionDateTimeLikeAssertion.YEAR.getDefault()
    val dayOfWeekDescr = DescriptionDateTimeLikeAssertion.DAY_OF_WEEK.getDefault()
    val dayDescr = DescriptionDateTimeLikeAssertion.DAY.getDefault()

    describeFun("val ${yearFeature.name}") {
        val yearVal = yearFeature.lambda

        context("ZonedDateTime with year 2009") {
            it("toBe(2009) holds") {
                fluent.yearVal().toBe(2009)
            }
            it("toBe(2019) fails") {
                expect {
                    fluent.yearVal().toBe(2019)
                }.toThrow<AssertionError> {
                    messageContains("$yearDescr: 2009")
                }
            }
        }
    }

    describeFun("fun ${year.name}") {
        val yearFun = year.lambda

        context("ZonedDateTime with year 2009") {
            it("is greater than 2009 holds") {
                fluent.yearFun { isGreaterThan(2008) }
            }
            it("is less than 2009 fails") {
                expect {
                    fluent.yearFun { isLessThan(2009) }
                }.toThrow<AssertionError> {
                    messageContains("$yearDescr: 2009")
                }
            }
        }
    }


    describeFun("val ${monthFeature.name}") {
        val monthVal = monthFeature.lambda

        context("ZonedDateTime with month May(5)") {
            it("toBe(May) holds") {
                fluent.monthVal().toBe(5)
            }
            it("toBe(March) fails") {
                expect {
                    fluent.monthVal().toBe(3)
                }.toThrow<AssertionError> {
                    messageContains("$monthDescr: 5" )
                }
            }
        }
    }

    describeFun("fun ${month.name}") {
        val monthFun = month.lambda

        context(  "ZonedDateTime with month May(5)") {
            it("is greater than February(2) holds") {
                fluent.monthFun { isGreaterThan(2) }
            }
            it("is less than 5 fails") {
                expect {
                    fluent.monthFun { isLessThan(5) }
                }.toThrow<AssertionError> {
                    messageContains("$monthDescr: 5")
                }
            }
        }
    }

    describeFun("val ${dayFeature.name}") {
        val dayVal = dayFeature.lambda

        context("LocalDate with day of month 15") {
            it("toBe(15) holds") {
                fluent.dayVal().toBe(15)
            }
            it("toBe(20) fails") {
                expect {
                    fluent.dayVal().toBe(20)
                }.toThrow<AssertionError> {
                    messageContains("$dayDescr: 15" )
                }
            }
        }
    }

    describeFun("fun ${day.name}") {
        val dayOfMonthFun = day.lambda

        context("LocalDate with day of month 15") {
            it("is greater than 5 holds") {
                fluent.dayOfMonthFun { isGreaterThan(5) }
            }
            it("is less than 5 fails") {
                expect {
                    fluent.dayOfMonthFun { isLessThan(5) }
                }.toThrow<AssertionError> {
                    messageContains("$dayDescr: 15" )
                }
            }
        }
    }

    describeFun("val ${dayOfWeekFeature.name}") {
        val dayOfWeekVal = dayOfWeekFeature.lambda

        context("ZonedDateTime with day of week Friday(5)") {
            it("toBe(Friday) holds") {
                fluent.dayOfWeekVal().toBe(DayOfWeek.FRIDAY)
            }
            it("toBe(Monday) fails") {
                expect {
                    fluent.dayOfWeekVal().toBe(DayOfWeek.MONDAY)
                }.toThrow<AssertionError> {
                    messageContains("$dayOfWeekDescr: ${DayOfWeek.FRIDAY}" )
                }
            }
        }
    }

    describeFun("fun ${dayOfWeek.name}") {
        val dayOfWeekFun = dayOfWeek.lambda

        context(  "ZonedDateTime with day of week Friday(5)") {
            it("is greater than Monday(1) holds") {
                fluent.dayOfWeekFun { isGreaterThan(DayOfWeek.MONDAY) }
            }
            it("is less than Friday(5) fails") {
                expect {
                    fluent.dayOfWeekFun { isLessThan(DayOfWeek.FRIDAY) }
                }.toThrow<AssertionError> {
                    messageContains("$dayOfWeekDescr: ${DayOfWeek.FRIDAY}" )
                }
            }
        }
    }
})
