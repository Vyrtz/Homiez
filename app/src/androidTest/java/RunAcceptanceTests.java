import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.group6.homiez.presentation.LoginActivityTest;
import comp3350.group6.homiez.presentation.SignupActivityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginActivityTest.class, SignupActivityTest.class})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Running Acceptance tests");
    }
}

