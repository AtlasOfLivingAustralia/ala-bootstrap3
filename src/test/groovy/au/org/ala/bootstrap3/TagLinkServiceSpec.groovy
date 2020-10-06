package au.org.ala.bootstrap3

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions.
 *
 * NOTE: The TagLinkService is tested indirectly in HeaderFooterTagLibSpec.
 */
@TestFor(TagLinkService)
class TagLinkServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test clearing cache"() {
        when:
        service.clearCache()

        then:
        service.hfCache.every { it.value.content == '' }
    }
}
