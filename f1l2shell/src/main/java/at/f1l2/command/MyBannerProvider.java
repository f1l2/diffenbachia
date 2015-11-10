package at.f1l2.command;

import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;
import org.springframework.core.Ordered;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyBannerProvider extends DefaultBannerProvider  {

	public String getBanner() {
		StringBuffer buf = new StringBuffer();
		buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
		buf.append("*                                     *"+ OsUtils.LINE_SEPARATOR);
		buf.append("*            f1l2 shell               *" +OsUtils.LINE_SEPARATOR);
		buf.append("*                                     *"+ OsUtils.LINE_SEPARATOR);
		buf.append("=======================================" + OsUtils.LINE_SEPARATOR);
		buf.append("Version:" + this.getVersion());
		return buf.toString();
	}

	public String getVersion() {
		return "0.0.1";
	}

	public String getWelcomeMessage() {
		return "Welcome to f1l2 shell";
	}
	
	@Override
	public String getProviderName() {
		return "f1l2 shell Banner";
	}
}
