package cn.jackbin.SimpleRecord.common.config.flow;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

@Component("c")
public class CCmp extends NodeComponent {

	@Override
	public void process() {
		//do your business
		System.out.println("c");
	}
}