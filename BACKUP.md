## 常用命令行

```
./gradlew dependencyUpdates
./gradlew clean
./gradlew iD -x lint --offline
```

[\u4e00-\u9fa5] 中文匹配正则表达式

## Anchors 说明

Anchors 定义不同的 TAG 用于过滤 log, 需要打开 Debug 模式。
- Anchors, 最基础的 TAG
- TASK_DETAIL, 过滤依赖任务的详情
- ANCHOR_DETAIL, 过滤输出锚点任务信息
- LOCK_DETAIL, 过滤输出等待信息
- DEPENDENCE_DETAIL, 过滤依赖图信息

## aar 使用说明