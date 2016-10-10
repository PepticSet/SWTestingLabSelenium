import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource(), containsString("Generic Business App"));

                browser.click("#employees");
                assertThat(browser.url(), containsString("employee"));

                browser.fill("#name_field").with("Karl");
                browser.fill("#salary_field").with("1000");
                browser.submit("#submit_employee");

//                List<FluentWebElement> lastRowName = browser.find("#employee-table").find("tr").find("td", withText("Karl"));
//                List<FluentWebElement> lastRowName = browser.find("#employees > tbody > tr:last-child > td:nth-child(2)");
                FluentWebElement lastRow = browser.find("#employee-table").find("tr:last-child").get(0);

//                assertTrue(lastRowName.contains("Karl"));
                assertTrue(lastRow.find("td", 1).getText().equals("Karl"));

            }
        });
    }

}
