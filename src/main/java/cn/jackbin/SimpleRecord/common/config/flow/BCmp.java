package cn.jackbin.SimpleRecord.common.config.flow;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

@Component("b")
public class BCmp extends NodeComponent {

	@Override
	public void process() {
		//do your business
		System.out.println("b");
	}
}