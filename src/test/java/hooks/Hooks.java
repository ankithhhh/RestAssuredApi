package hooks;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.*;

public class Hooks {
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

	@BeforeAll
	public static void setupReport() {
		ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		test = extent.createTest(scenario.getName()); // fixed: use class-level 'test'
		extentTestThreadLocal.set(test);
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		if (scenario.isFailed()) {
			extentTestThreadLocal.get().fail("Step failed");
		}
	}

	@AfterAll
	public static void tearDownReport() {
		extent.flush(); // final flush after all scenarios
	}

	public static ExtentTest getTest() {
		return extentTestThreadLocal.get();
	}
}
