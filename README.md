# console三国杀

**开发中**

控制台版三国杀，java实现

尚未制作GUI（~~控制台才是灵魂！！~~）

纯属娱乐项目

by wzk





## 更新日志

* 6.5  初次commit
* 6.5  增加三尖两刃刀；修改了`IO.chooseFromProvided`；
* 6.5  增加丈八蛇矛、贯石斧；增加了`useSkillInUsePhase`接口（用于出牌阶段主动发动技能）
* 6.6  增加借刀、南蛮万剑、桃园五谷等锦囊牌；修改了`Person.hurt`；
* 6.6  牌堆`CardsHeap.init`中完成基本牌初始化
* 6.7  完成了`CardsHeap.init`中所有牌的初始化，可以玩🌶；修改了过拆、顺牵
* 6.7  通过测试修复了若干bug，增加若干IO输出；**3000行撒花！💐**
* 6.9  增加了注解技能类`Skill`及觉醒技、锁定技等（现学现用）；开始添加武将：郭嘉荀彧；
* 6.9  增加了国籍和身份（实际上是因为主公技而不得不加），可以打身份局🌶；增加了刘关张、曹操等武将
* 6.9  `README`中增加了更新日志；
* 6.10  增加了马超黄忠赵云（五虎将集齐）、诸葛亮黄月英（标包蜀国完成）；增加了牌数检查；修复了小bug
* 6.10  增加了司马懿夏侯惇；修复了死亡时未弃置所有牌的bug；更正了黄月英的性别；**4000行打卡✌️**
* 6.11  增加了许褚张辽甄姬（标包魏国完成）；对losecard和throwcard做小改动；修复了判定牌未进入弃牌堆的问题



```
TODO list:
 简化IO为Person类实现的接口
```



## 设计

### 管理类

#### GameManager

* 负责游戏宏观运行（每名玩家依次出牌、判定胜负条件等）
* 提供涉及到多个玩家的调用接口
  * AOE、桃园、五谷等范围卡牌
  * 询问桃、无懈、改判
  * 查询玩家之间的距离

如果将整个游戏理解为操作系统，那么GameManager相当于内核，提供系统调用帮助卡牌和武将完成功能。

#### CardsHeap

* 负责牌堆和弃牌的管理，提供摸牌、弃牌与判定接口
* 牌堆为空时自动洗牌的功能
* 为诸葛亮、神吕蒙、李典等提供查看牌堆与操控牌堆的接口

#### IO

* 封装系统IO接口，例如输出卡牌信息、获得输入内容

* 提供用户选择玩家（由GameManager代为完成）、选择选项、选择卡牌的接口

#### Utils

* 提供常用工具包，例如断言、深拷贝、随机整数等

### 人员类

#### SkillLauncher

接口，设定了一些技能发动时机的方法，默认均为空函数体，Person类实现该接口。若武将需要设置技能，可重写方法。

#### PersonAttributes

抽象类，设置了翻面、铁索连环、喝酒、死亡、性别等相关属性，由于与Person类的多数方法没有交互因而单独分出。~~真实原因：Person类太臃肿了需要拆出一些不然过不了checkstyle。~~后续还会增加一些和武将技能有关的通用属性（例如梦魇标记等）。

#### Person

抽象类，武将行为的核心，属性主要为HP和现有卡牌等，行为包括回合各个阶段、摸排弃牌、增减体力、询问打出卡牌等，并内置了所有防具类装备的处理逻辑。



### 其余重要类

#### Sha

杀卡牌，继承自BasicCard，包含了杀伤害的属性（火、雷、普通）。内置了大多数武器类的处理逻辑（除诸葛连弩和丈八蛇矛在Person类中），并调用了各个杀相关的技能发动时机方法。



## 支持卡牌

### 基本牌

| 名称             | 已完成 |
| ---------------- | ------ |
| 杀（火杀、雷杀） | √      |
| 闪               | √      |
| 桃               | √      |
| 酒               | √      |



### 锦囊牌

延时锦囊

| 名称     | 已完成 |
| -------- | ------ |
| 闪电     | √      |
| 乐不思蜀 | √      |
| 兵粮寸断 | √      |

非延时锦囊

| 名称     | 已完成 |
| -------- | ------ |
| 决斗     | √      |
| 过河拆桥 | √      |
| 顺手牵羊 | √      |
| 无中生有 | √      |
| 无懈可击 | √      |
| 铁索连环 | √      |
| 火攻     | √      |
| 万箭齐发 | √      |
| 南蛮入侵 | √      |
| 桃园结义 | √      |
| 五谷丰登 | √      |
| 借刀杀人 | √      |
| 以逸待劳 | —      |
| 知己知彼 | —      |
| 远交近攻 | —      |





### 装备牌

武器

| 名称       | 已完成 |
| ---------- | ------ |
| 雌雄双股剑 | √      |
| 青釭剑     | √      |
| 贯石斧     | √      |
| 青龙偃月刀 | √      |
| 麒麟弓     | √      |
| 寒冰剑     | √      |
| 古锭刀     | √      |
| 吴六剑     | —      |
| 朱雀羽扇   | √      |
| 诸葛连弩   | √      |
| 丈八蛇矛   | √      |
| 三尖两刃刀 | √      |



防具

| 名称     | 已完成 |
| -------- | ------ |
| 白银狮子 | √      |
| 藤甲     | √      |
| 仁王盾   | √      |
| 八卦阵   | √      |



马

| 名称     | 已完成 |
| -------- | ------ |
| 骅骝     | √      |
| 绝影     | √      |
| 爪黄飞电 | √      |
| 的卢     | √      |
| 紫骍     | √      |
| 赤兔     | √      |
| 大宛     | √      |



## 武将

### 标准包

#### 魏国

| 名称   | 已完成 |
| ------ | ------ |
| 曹操   | √      |
| 郭嘉   | √      |
| 司马懿 | √      |
| 夏侯惇 | √      |
| 甄姬   | √      |
| 张辽   | √      |
| 许褚   | √      |



#### 蜀国

| 名称   | 已完成 |
| ------ | ------ |
| 刘备   | √      |
| 关羽   | √      |
| 张飞   | √      |
| 赵云   | √      |
| 马超   | √      |
| 诸葛亮 | √      |
| 黄月英 | √      |



#### 风包

| 名称 | 已完成 |
| ---- | ------ |
| 黄忠 | √      |





### 火包

| 名称 | 已完成 |
| ---- | ------ |
| 荀彧 | √      |

