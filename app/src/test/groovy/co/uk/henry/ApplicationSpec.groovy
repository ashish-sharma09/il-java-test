package co.uk.henry

import spock.lang.Specification

class ApplicationSpec extends Specification {

    def "Application is initialized with correct dependencies"() {
        when: "An application instance is created"
        Application application = new Application();

        then: "services are initialized"
        application.getBasketService() != null
        application.getProductService() != null
    }
}
