package com.coding.common.config.jpa;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 审计设置
 * TODO: 2021/2/23 未完，后期创建人和修改人从security中获取，https://blog.csdn.net/lxh_worldpeace/article/details/105582259
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * @return 返回操作员标志信息
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        //return Optional.empty();
        return Optional.of("System");
    }


}
