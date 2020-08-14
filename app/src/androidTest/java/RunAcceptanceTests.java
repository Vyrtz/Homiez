import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.group6.homiez.presentation.CustomizeProfileAcceptanceTest;
import comp3350.group6.homiez.presentation.LoginAcceptanceTest;
import comp3350.group6.homiez.presentation.MatchesAcceptanceTest;
import comp3350.group6.homiez.presentation.PostingAcceptanceTest;
import comp3350.group6.homiez.presentation.RequestsAcceptanceTest;
import comp3350.group6.homiez.presentation.SignupAcceptanceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginAcceptanceTest.class, SignupAcceptanceTest.class, PostingAcceptanceTest.class, CustomizeProfileAcceptanceTest.class, MatchesAcceptanceTest.class, RequestsAcceptanceTest.class})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Running Acceptance tests");
    }
}

