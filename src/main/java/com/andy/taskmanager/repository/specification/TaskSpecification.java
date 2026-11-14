package com.andy.taskmanager.repository.specification;

import com.andy.taskmanager.entity.Task;
import org.springframework.data.jpa.domain.Specification;

// Builds dynamic WHERE clauses for Task queries.
public class TaskSpecification {
    private TaskSpecification() {}

    public static Specification<Task> hasTitle(String title) {

        /*
         * [Criteria API]
         * 1. root: 代表查询的实体根 (相当于 FROM Task)。通过 root.get("字段") 获取列。
         * 2. query: 代表整个顶层查询 (含 SELECT/FROM/ORDER BY)。通常由外层 Repository 接管，Specification 内部极少使用。
         * 3. criteriaBuilder (cb): 条件构建器。专门用于拼接 WHERE 子句 (like, equal, lower 等)。
         *
         * 核心机制：若参数为空，返回 null。Spring 会自动忽略该 null 条件，实现动态 SQL 拼接。
         */
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return null;
            }

            /*
             * [LIKE]
             * 在 SQL 数据库里，LIKE专门用来做模糊匹配。
             * LIKE 必须配合一个特殊符号 %（百分号）使用：
             *
             * LIKE '吃饭' ：必须完全等于“吃饭”（跟 = 一样，没意义）。
             * LIKE '吃饭%' ：以“吃饭”开头（比如“吃饭了吗”）。
             * LIKE '%吃饭' ：以“吃饭”结尾（比如“去吃饭”）。
             * LIKE '%吃饭%'：只要包含“吃饭”就行（比如“我要去吃饭了”）。
             */
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Task> hasCompleted(Boolean completed) {
        return (root, query, criteriaBuilder) -> {
            if (completed == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("completed"), completed);
        };
    }
}
