package at.f1l2.command;

import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;
import org.springframework.core.Ordered;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyPromptProvider extends DefaultPromptProvider {

	@Override
	public String getPrompt() {
		return "f1l2>";
	}

	
	@Override
	public String getProviderName() {
		return "Prompt provider";
	}

}
