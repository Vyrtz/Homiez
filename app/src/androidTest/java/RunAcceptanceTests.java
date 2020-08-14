import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.group6.homiez.presentation.LoginTest;
import comp3350.group6.homiez.presentation.SignupTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginTest.class, SignupTest.class})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Running Acceptance tests");
    }
}

