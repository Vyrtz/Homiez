import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.group6.homiez.presentation.LoginActivityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginActivityTest.class})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Running Acceptance tests");
    }
}

