package com.databps.bigdaf.admin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShellCommandUtilsTest {

	@Test
	public void testCommand() throws IOException, InterruptedException{
		
		List<String> command = new ArrayList<>(); 
		command.add("ps");
		command.add("-ef");
		ShellCommandUtils.Result result = ShellCommandUtils.runCommand(command.toArray(new String[command.size()]));
		System.out.println(result.getStdout());
		System.out.println(result.getStderr());
	}
}
