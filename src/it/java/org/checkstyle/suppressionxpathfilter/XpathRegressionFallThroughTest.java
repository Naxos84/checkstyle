////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck;

public class XpathRegressionFallThroughTest extends AbstractXpathRegressionTest {

    @Test
    public void testOne() throws Exception {
        final String checkName = FallThroughCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionExplicitOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FallThroughCheck.class);

        final String[] expectedViolation = {
            "11:13: " + getCheckMessage(FallThroughCheck.class,
                FallThroughCheck.MSG_FALL_THROUGH),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/SLIST/LITERAL_SWITCH/CASE_GROUP",
            "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_CASE"
        );

        runVerifications(moduleConfig, checkName, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = FallThroughCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionExplicitTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FallThroughCheck.class);
        moduleConfig.addAttribute("checkLastCaseGroup", "true");

        final String[] expectedViolation = {
            "10:17: " + getCheckMessage(FallThroughCheck.class,
                FallThroughCheck.MSG_FALL_THROUGH_LAST),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitTwo']/OBJBLOCK"
                + "/METHOD_DEF[@text='methodFallThruCustomWords']/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP",
            "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitTwo']/OBJBLOCK"
                + "/METHOD_DEF[@text='methodFallThruCustomWords']/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, checkName, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
