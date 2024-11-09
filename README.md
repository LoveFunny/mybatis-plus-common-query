# mybatis-plus-common-query
# 背景
后端微服务单个接口要支持前端不同的查询场景，由于不同场景下的查询条件不同，新增场景需要接口适配。比如之前A场景支持对字段1、字段2的查询，现在新增B场景需要对字段3、字段4的查询且需要新增排序。</br>
为了省略后端适配工作量，所以有了这个支持前端自定义查询和排序的工具类

# 说明
该工具只适用一般业务查询接口，对接口查询速度有要求的建议还是自己手写</br>
该工具暴漏的是数据库字段，对此敏感的不建议使用，或者自己在上层加一个查询字段转数据库字段的方法</br>
该工具默认暴漏的是Entity中所有被TableField和TableId注解标识的字段</br>
该工具的like查询试用的%%，有前缀匹配要求的可按需修改工具类中的like查询逻辑

# 使用
后端将前端传参序列化成Filter类，之后调用Filter2Wrapper工具类的filter2Wrapper方法，将Filter转成mybatis-plus的Wrapper类

# 用法
example文件夹下的Test类里面有示例
