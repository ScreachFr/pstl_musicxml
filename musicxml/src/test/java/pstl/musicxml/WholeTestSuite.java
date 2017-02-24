package pstl.musicxml;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		pstl.musicxml.ParserTest.class,
		pstl.musicxml.ScoreUtilsTest.class
})
public class WholeTestSuite {}
