package br.ufrn.fm;

import br.ufrn.fm.filesAccess.Command;
import br.ufrn.fm.models.MakeDirDetails;
import br.ufrn.fm.services.CommandsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class FmApplicationTests {

	@Test
	void contextLoads() {
		Command command = new Command("storage");
		CommandsService commandsService = new CommandsService(command);
		String path = "teste/outro";
		MakeDirDetails makeDirDetails = new MakeDirDetails();
		makeDirDetails.setPath(path);
		makeDirDetails.setRecursive(true);
		commandsService.createDirectory(makeDirDetails);
		System.out.println("Teste");
	}

}
