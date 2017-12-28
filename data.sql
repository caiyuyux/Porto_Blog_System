/*
 Navicat Premium Data Transfer

 Source Server         : Postgress
 Source Server Type    : PostgreSQL
 Source Server Version : 90604
 Source Host           : localhost:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90604
 File Encoding         : 65001

 Date: 29/12/2017 00:32:02
*/


-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS "categories";
CREATE TABLE "categories" (
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "count" int4,
  "account" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "categories" OWNER TO "postgres";

-- ----------------------------
-- Records of categories
-- ----------------------------
BEGIN;
INSERT INTO "categories" VALUES ('二次元向', 1, 'caiyuyu');
INSERT INTO "categories" VALUES ('学习笔记', 2, 'caiyuyu');
COMMIT;

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS "images";
CREATE TABLE "images" (
  "filename" varchar(255) COLLATE "pg_catalog"."default",
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "postid" varchar(255) COLLATE "pg_catalog"."default",
  "time" timestamp(6),
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "images" OWNER TO "postgres";

-- ----------------------------
-- Records of images
-- ----------------------------
BEGIN;
INSERT INTO "images" VALUES ('3.png', 'caiyuyu', '/templates/business/caiyuyu/images/3.png', NULL, '2017-12-28 23:51:21.313', 'image');
INSERT INTO "images" VALUES ('1.jpg', 'caiyuyu', '/templates/business/caiyuyu/images/1.jpg', NULL, '2017-12-28 23:51:56.436', 'image');
INSERT INTO "images" VALUES ('000633ut4ecz5ltico35w4.jpg', 'caiyuyu', '/templates/business/caiyuyu/images/000633ut4ecz5ltico35w4.jpg', NULL, '2017-12-29 00:12:22.108', 'image');
INSERT INTO "images" VALUES ('153335l1jddjtd1k9ddyzt.jpg', 'caiyuyu', '/templates/business/caiyuyu/images/153335l1jddjtd1k9ddyzt.jpg', NULL, '2017-12-29 00:12:22.13', 'image');
COMMIT;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS "news";
CREATE TABLE "news" (
  "obj" varchar(255) COLLATE "pg_catalog"."default",
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "image" varchar(255) COLLATE "pg_catalog"."default",
  "video" varchar(255) COLLATE "pg_catalog"."default",
  "music" varchar(255) COLLATE "pg_catalog"."default",
  "post" varchar(255) COLLATE "pg_catalog"."default",
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "news" OWNER TO "postgres";

-- ----------------------------
-- Records of news
-- ----------------------------
BEGIN;
INSERT INTO "news" VALUES ('signup', 'caiyuyu', NULL, NULL, NULL, NULL, '注册了账号', '2017-12-28 21:32:01.795', 'add');
INSERT INTO "news" VALUES ('mind', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了心情', '2017-12-28 21:52:29.545', 'update');
INSERT INTO "news" VALUES ('profile', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了资料', '2017-12-28 22:01:26.312', 'update');
INSERT INTO "news" VALUES ('mind', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了心情', '2017-12-29 00:19:22.74', 'update');
INSERT INTO "news" VALUES ('profile', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了资料', '2017-12-29 00:20:28.5', 'update');
INSERT INTO "news" VALUES ('profile', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了资料', '2017-12-29 00:21:39.07', 'update');
INSERT INTO "news" VALUES ('profile', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了资料', '2017-12-29 00:21:52.243', 'update');
INSERT INTO "news" VALUES ('profile', 'caiyuyu', NULL, NULL, NULL, NULL, '更新了资料', '2017-12-29 00:23:05.11', 'update');
COMMIT;

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS "posts";
CREATE TABLE "posts" (
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "tags" varchar(255) COLLATE "pg_catalog"."default",
  "md" text COLLATE "pg_catalog"."default",
  "html" text COLLATE "pg_catalog"."default",
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "image" varchar(255) COLLATE "pg_catalog"."default",
  "video" varchar(255) COLLATE "pg_catalog"."default",
  "music" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "time" timestamp(6),
  "categories" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "posts" OWNER TO "postgres";

-- ----------------------------
-- Records of posts
-- ----------------------------
BEGIN;
INSERT INTO "posts" VALUES ('/templates/business/caiyuyu/posts/火影—15年完结纪念致我的雏田大小姐.html', '火影—15年完结纪念致我的雏田大小姐', 'にじげん', '我们在听故事的时候，一直喜欢问：后来呢后来呢？但是并不是每个故事都会有结果，所幸，我们等到了，火影历经15年，终于完结了，更加喜闻乐见的、我们一直等着的，我们的雏田大小姐终于修成正果，如愿以偿了，意料之外也意料之中。  

<!--more-->

所以呢？我还记得当时刚刚看火影时的震撼，在看的过程中也确实带给我很多收获和享受，但是时至今日，火影对我来说，更多的是一种情怀的味道，是对小学中学的一个回味，而雏田是我最喜欢一个人，我之所以称之为“人”而非“人物”，是因为我不愿意单单将其停留在火影这个世界里面。

雏田很可爱很可爱很可爱，重要的事情说三遍，雏田是个很柔弱、很容易害羞的小女生，动不动就脸红，手足无措的戳手指，然而她并不只是个花瓶，她是典型的外柔内刚，也有坚强的一面，执拗起来也会让你很头疼。

可惜，幸福的路上总是曲折的，雏田遇上了不解风情的鸣人，只能望着鸣人的背影，一直追逐着，也变得越来越坚强，但不够，雏田有了自己的想法，不满足于跟在喜欢的人的身后，她想与之肩并肩，很好，她成功了。

鸣人是幸运的，因为有雏田一直等着他，雏田也是幸运的，因为她终于等来了鸣人的后知后觉；如果要用一句话来形容这段感情，那么我只能说：

>终于等到你，还好我没放弃。

![](http://localhost/templates/business/caiyuyu/images/1.jpg)

<embed src="http://music.163.com/style/swf/widget.swf?sid=27836179&type=2&auto=1&width=278&height=32" width="298" height="52"  allowNetworking="all"></embed>', '<p>我们在听故事的时候，一直喜欢问：后来呢后来呢？但是并不是每个故事都会有结果，所幸，我们等到了，火影历经15年，终于完结了，更加喜闻乐见的、我们一直等着的，我们的雏田大小姐终于修成正果，如愿以偿了，意料之外也意料之中。  </p>
<!--more-->

<p>所以呢？我还记得当时刚刚看火影时的震撼，在看的过程中也确实带给我很多收获和享受，但是时至今日，火影对我来说，更多的是一种情怀的味道，是对小学中学的一个回味，而雏田是我最喜欢一个人，我之所以称之为“人”而非“人物”，是因为我不愿意单单将其停留在火影这个世界里面。</p>
<p>雏田很可爱很可爱很可爱，重要的事情说三遍，雏田是个很柔弱、很容易害羞的小女生，动不动就脸红，手足无措的戳手指，然而她并不只是个花瓶，她是典型的外柔内刚，也有坚强的一面，执拗起来也会让你很头疼。</p>
<p>可惜，幸福的路上总是曲折的，雏田遇上了不解风情的鸣人，只能望着鸣人的背影，一直追逐着，也变得越来越坚强，但不够，雏田有了自己的想法，不满足于跟在喜欢的人的身后，她想与之肩并肩，很好，她成功了。</p>
<p>鸣人是幸运的，因为有雏田一直等着他，雏田也是幸运的，因为她终于等来了鸣人的后知后觉；如果要用一句话来形容这段感情，那么我只能说：</p>
<blockquote>
<p>终于等到你，还好我没放弃。</p>
</blockquote>
<p><img src="http://localhost/templates/business/caiyuyu/images/1.jpg" alt=""></p>
<embed src="http://music.163.com/style/swf/widget.swf?sid=27836179&amp;type=2&amp;auto=1&amp;width=278&amp;height=32" width="298" height="52" allownetworking="all">', 'caiyuyu', 'http://localhost/templates/business/caiyuyu/images/3.png', '', NULL, '2017-12-28 23:55:05.792', 'document', '2017-12-28 23:51:21.309', '二次元向');
INSERT INTO "posts" VALUES ('/templates/business/caiyuyu/posts/Clojure学习日记—0序言.html', 'Clojure学习日记—0序言', 'clojure', '吼吼吼，clojure你别跑，我来了，啦啦啦！

<!--more-->

### lisp介绍

说起clojure可能没多少人知道，可能最多只知道她是一种函数式语言、lisp方言，类似common lisp，  

lisp的设计思想是公认的强大，有人甚至说当今最高级的主流语言，也只是刚刚接近1958年出生的Lisp所在的水平，
而且之后发展的语言都有借鉴部分的lisp的思想，像java、python、ruby等。 

现在lisp作为一种小众语种，知道的人少，学习的人更少了，这其中的曲曲折折、坑坑洼洼我也不是很了解，  
到底是什么原因导致一个如此牛掰的语言沦落至此？原因总结如下(我是没资格说这种话的，参考大牛)  

> 1. 最最大的原因，生态圈有问题，因为她太太灵活了，你根本不可能制作出一款IDE来
> 2. lisp残缺不全，源码其实只写了一部分，但是就是这一部分包括宏定义这些就可以让常人仰望了
> 3. lisp是一种研究型、实验室语言，应用不广，无法推广。

在我大二的时候很迷恋lisp，看了一点语法，大概了解了lisp现状最后不得不不了了之，难是一部分原因，主要是你付出后得到的回报几乎微乎其微。  
综合这些来看，其实现在有人学习lisp主要出于一种图腾崇拜思想去学习它，类似用emacs喝咖啡的感觉。  

***

### clojure介绍

虽然lisp现在发展几乎停滞，但是取lisp精华，去其糟粕的变种语言如Scheme、clojure却一直在前进。  
2015暑假，在我大三的这一年我美丽地邂逅了clojure，又重新燃起我对lisp的喜爱，clojure有很多优点，我只说我比较看重的几点。  

> 1. 基于jvm平台的lisp变种，弥补的lisp那可怕的生态圈又保持着lisp的灵活高效。  
> 2. 可以被应用到企业上，据我所知clojure有一些优秀的web框架，如果学好clojure可能还能找的一个不错的工作。  
> 3. clojure+clojureScript几乎可以实现全栈，用一种语言实现全栈，想想该有多舒坦，而且还是这种简洁的语言。  
> 4. clojure的社区还是比较活跃的，在国内是有点冷淡，但是国外还是很热的，很多人在讨论她。

综上所述，clojure的这个坑我是掉定了，我也愿意！  

***

### 关于本文

2015下半年断断续续的学了一点时间的clojure语法，但是只是囫囵吞枣，感觉什么都没学到，再加上学校课程也挺紧的，
索性推翻，重新学一遍，谨以本文做个记录，记录下心得，记录下我大clojure的成长之路。', '<p>吼吼吼，clojure你别跑，我来了，啦啦啦！</p>
<!--more-->

<h3 id="lisp-">lisp介绍</h3>
<p>说起clojure可能没多少人知道，可能最多只知道她是一种函数式语言、lisp方言，类似common lisp，  </p>
<p>lisp的设计思想是公认的强大，有人甚至说当今最高级的主流语言，也只是刚刚接近1958年出生的Lisp所在的水平，
而且之后发展的语言都有借鉴部分的lisp的思想，像java、python、ruby等。 </p>
<p>现在lisp作为一种小众语种，知道的人少，学习的人更少了，这其中的曲曲折折、坑坑洼洼我也不是很了解，<br>到底是什么原因导致一个如此牛掰的语言沦落至此？原因总结如下(我是没资格说这种话的，参考大牛)  </p>
<blockquote>
<ol>
<li>最最大的原因，生态圈有问题，因为她太太灵活了，你根本不可能制作出一款IDE来</li>
<li>lisp残缺不全，源码其实只写了一部分，但是就是这一部分包括宏定义这些就可以让常人仰望了</li>
<li>lisp是一种研究型、实验室语言，应用不广，无法推广。</li>
</ol>
</blockquote>
<p>在我大二的时候很迷恋lisp，看了一点语法，大概了解了lisp现状最后不得不不了了之，难是一部分原因，主要是你付出后得到的回报几乎微乎其微。<br>综合这些来看，其实现在有人学习lisp主要出于一种图腾崇拜思想去学习它，类似用emacs喝咖啡的感觉。  </p>
<hr>
<h3 id="clojure-">clojure介绍</h3>
<p>虽然lisp现在发展几乎停滞，但是取lisp精华，去其糟粕的变种语言如Scheme、clojure却一直在前进。<br>2015暑假，在我大三的这一年我美丽地邂逅了clojure，又重新燃起我对lisp的喜爱，clojure有很多优点，我只说我比较看重的几点。  </p>
<blockquote>
<ol>
<li>基于jvm平台的lisp变种，弥补的lisp那可怕的生态圈又保持着lisp的灵活高效。  </li>
<li>可以被应用到企业上，据我所知clojure有一些优秀的web框架，如果学好clojure可能还能找的一个不错的工作。  </li>
<li>clojure+clojureScript几乎可以实现全栈，用一种语言实现全栈，想想该有多舒坦，而且还是这种简洁的语言。  </li>
<li>clojure的社区还是比较活跃的，在国内是有点冷淡，但是国外还是很热的，很多人在讨论她。</li>
</ol>
</blockquote>
<p>综上所述，clojure的这个坑我是掉定了，我也愿意！  </p>
<hr>
<h3 id="-">关于本文</h3>
<p>2015下半年断断续续的学了一点时间的clojure语法，但是只是囫囵吞枣，感觉什么都没学到，再加上学校课程也挺紧的，
索性推翻，重新学一遍，谨以本文做个记录，记录下心得，记录下我大clojure的成长之路。</p>
', 'caiyuyu', 'http://localhost/templates/business/caiyuyu/images/153335l1jddjtd1k9ddyzt.jpg', '', NULL, '2017-12-29 00:13:38.108', 'document', '2017-12-28 23:58:39.56', '学习笔记');
INSERT INTO "posts" VALUES ('/templates/business/caiyuyu/posts/Clojure学习日记—1入门.html', 'Clojure学习日记—1入门', 'clojure', '这个系列只是笔记，本非博文什么的，写给自己看，纯粹做个记录，里面的观点看法只是个人见解，仅作参考。

<!--more-->
### 学习教材
嗯，废话不多说，教材用的是《Clojure Programming》，也就是俗称的动物书，听说比较全面，打基础不错。

当然，是翻译版的，英语水平不够是硬伤呀。 可以点 [Clojure Programming.pdf](http://leanote.com/api/file/getAttach?fileId=56a72482ab6441777600233a) 下载英文版PDF文档。
- - -
### Clojure特点
1. 基于JVM平台，lisp恶劣的生态圈和稳定的JVM一结合，赶脚是天造地设的一对。

2. 函数式编程语言，不可否认，函数式编程确实可以锻炼你的思维。

3. 天生适合进行并行、并发编程。

4. 动态编程语言，可以交互式开发。
- - -
### Clojure的同像性
同像性一般称为“代码即数据”，即是说clojure没有太多臃肿的代码片段，这也是元编程的前提。  

从代码转换成AST(抽象语法树)是一门语言的核心，也是体现一门语言的表达能力的所在，

但是你要作出这个转换一般的前提是你对这么语言有一定的理解，理解的程度和该门语言的表达能力成反比，但是具有同像性的语言是个例外，

因为clojure的代码是直接用AST的数据结构来写的，就是说，你写下的clojure代码就是一个AST了，不需要特意去转换，

这也意味着clojure拥有其他语言所不能及的优秀的表达能力。
- - -
### Clojure跑起来

#### 必备jar包

1. JRE；也就是java运行时，当然，有JDK肯定也是可以的。

2. clojure.jar；可以在官网下载，现在最新的是1.8。

#### 编程工具

1. Emacs；神器来着，可惜我驾驭不了，号称天生适合开发lisp，不适合新手，套用大牛的话，因为学习曲线太陡峭，除非你是emacs大牛。

2. Eclipse；没用过，用了一段时间的myeclipse，已弃用。

3. IDEA；没得说，强烈推荐，对clojure的支持很好，不过注意，不用La Clojure插件，用cursive插件，同时支持Leiningen，可惜要付费，不过真的很值，可以试用看看先。

4. 命令行；这个不太熟悉，之前很久搞过，好像是要用lein运行的，不过用命令行，想想都觉得累，大牛走开、走开。
- - -
### REPL是什么鬼

#### 简单介绍

即 Read(读入)-Eval(求值)-Print(打印)-Loop(循环)。

其实很多语言都都有repl这个东东，就是俗称的解释器，解析代码用的，像java也有BeanShell。

但是clojure的repl是有点不同的，clojure的repl在解析代码的时候有个编译的过程，会编译成JVM的字节码，即是说clojure不是像html那种边解析边执行的，它是要编译成字节码后才能执行的，只不过clojure在解释执行的时候顺便编译了，跟直接执行一个clojure源文件是一样的，这点其实我也有点懵。

clojure有repl肯定是个不小的有点，因为这意味着clojure可以交互，可以在运行时更新现有或新的代码，类似phtyon，可以对比下java的web，更新代码都要重启服务器，如果是clojure来实现，就支持热部署。

#### 敲入代码

运行REPL，看到如下界面

`user=>`

这个clojure是有命名空间的概念，user表示你当前的命名空间，user是默认命名空间。

我们定义一个函数average，可以传入一个数组求平均值。
```clojure
(defn average    
    [numbers]
    (/ (apply + numbers) (count numbers)
```
然后调用代码得到结果
```clojure
(average [60 80 100 400]
;=160
```
注意 `;= `  前缀表示函数结果输出，在函数式语言中，函数是头等公民，都具有一个返回值，160就是average的返回值，也就是我们要求的平均值结果。

如果换成这样就变成这样
```clojure
(println (average [60 80 100 499]))
;160
;=nil
```
先求值`average`得到160的结果，`println`函数打印结果为 ;160  

但是这个打印的功能只是附加的，每个函数都要求值才是repl本身就有的，`println` 求值为空，

即`nil`', '<p>这个系列只是笔记，本非博文什么的，写给自己看，纯粹做个记录，里面的观点看法只是个人见解，仅作参考。</p>
<p><!--more--></p>
<h3 id="-">学习教材</h3>
<p>嗯，废话不多说，教材用的是《Clojure Programming》，也就是俗称的动物书，听说比较全面，打基础不错。</p>
<p>当然，是翻译版的，英语水平不够是硬伤呀。 可以点 <a href="http://leanote.com/api/file/getAttach?fileId=56a72482ab6441777600233a">Clojure Programming.pdf</a> 下载英文版PDF文档。</p>
<hr>
<h3 id="clojure-">Clojure特点</h3>
<ol>
<li><p>基于JVM平台，lisp恶劣的生态圈和稳定的JVM一结合，赶脚是天造地设的一对。</p>
</li>
<li><p>函数式编程语言，不可否认，函数式编程确实可以锻炼你的思维。</p>
</li>
<li><p>天生适合进行并行、并发编程。</p>
</li>
<li><p>动态编程语言，可以交互式开发。</p>
</li>
</ol>
<hr>
<h3 id="clojure-">Clojure的同像性</h3>
<p>同像性一般称为“代码即数据”，即是说clojure没有太多臃肿的代码片段，这也是元编程的前提。  </p>
<p>从代码转换成AST(抽象语法树)是一门语言的核心，也是体现一门语言的表达能力的所在，</p>
<p>但是你要作出这个转换一般的前提是你对这么语言有一定的理解，理解的程度和该门语言的表达能力成反比，但是具有同像性的语言是个例外，</p>
<p>因为clojure的代码是直接用AST的数据结构来写的，就是说，你写下的clojure代码就是一个AST了，不需要特意去转换，</p>
<p>这也意味着clojure拥有其他语言所不能及的优秀的表达能力。</p>
<hr>
<h3 id="clojure-">Clojure跑起来</h3>
<h4 id="-jar-">必备jar包</h4>
<ol>
<li><p>JRE；也就是java运行时，当然，有JDK肯定也是可以的。</p>
</li>
<li><p>clojure.jar；可以在官网下载，现在最新的是1.8。</p>
</li>
</ol>
<h4 id="-">编程工具</h4>
<ol>
<li><p>Emacs；神器来着，可惜我驾驭不了，号称天生适合开发lisp，不适合新手，套用大牛的话，因为学习曲线太陡峭，除非你是emacs大牛。</p>
</li>
<li><p>Eclipse；没用过，用了一段时间的myeclipse，已弃用。</p>
</li>
<li><p>IDEA；没得说，强烈推荐，对clojure的支持很好，不过注意，不用La Clojure插件，用cursive插件，同时支持Leiningen，可惜要付费，不过真的很值，可以试用看看先。</p>
</li>
<li><p>命令行；这个不太熟悉，之前很久搞过，好像是要用lein运行的，不过用命令行，想想都觉得累，大牛走开、走开。</p>
</li>
</ol>
<hr>
<h3 id="repl-">REPL是什么鬼</h3>
<h4 id="-">简单介绍</h4>
<p>即 Read(读入)-Eval(求值)-Print(打印)-Loop(循环)。</p>
<p>其实很多语言都都有repl这个东东，就是俗称的解释器，解析代码用的，像java也有BeanShell。</p>
<p>但是clojure的repl是有点不同的，clojure的repl在解析代码的时候有个编译的过程，会编译成JVM的字节码，即是说clojure不是像html那种边解析边执行的，它是要编译成字节码后才能执行的，只不过clojure在解释执行的时候顺便编译了，跟直接执行一个clojure源文件是一样的，这点其实我也有点懵。</p>
<p>clojure有repl肯定是个不小的有点，因为这意味着clojure可以交互，可以在运行时更新现有或新的代码，类似phtyon，可以对比下java的web，更新代码都要重启服务器，如果是clojure来实现，就支持热部署。</p>
<h4 id="-">敲入代码</h4>
<p>运行REPL，看到如下界面</p>
<p><code>user=&gt;</code></p>
<p>这个clojure是有命名空间的概念，user表示你当前的命名空间，user是默认命名空间。</p>
<p>我们定义一个函数average，可以传入一个数组求平均值。</p>
<pre><code class="lang-clojure">(<span class="hljs-name"><span class="hljs-builtin-name">defn</span></span> average    
    [numbers]
    (/ (<span class="hljs-name"><span class="hljs-builtin-name">apply</span></span> + numbers) (<span class="hljs-name"><span class="hljs-builtin-name">count</span></span> numbers)
</code></pre>
<p>然后调用代码得到结果</p>
<pre><code class="lang-clojure">(<span class="hljs-name">average</span> [<span class="hljs-number">60</span> <span class="hljs-number">80</span> <span class="hljs-number">100</span> <span class="hljs-number">400</span>]
<span class="hljs-comment">;=160</span>
</code></pre>
<p>注意 <code>;=</code>  前缀表示函数结果输出，在函数式语言中，函数是头等公民，都具有一个返回值，160就是average的返回值，也就是我们要求的平均值结果。</p>
<p>如果换成这样就变成这样</p>
<pre><code class="lang-clojure">(<span class="hljs-name">println</span> (<span class="hljs-name">average</span> [<span class="hljs-number">60</span> <span class="hljs-number">80</span> <span class="hljs-number">100</span> <span class="hljs-number">499</span>]))
<span class="hljs-comment">;160</span>
<span class="hljs-comment">;=nil</span>
</code></pre>
<p>先求值<code>average</code>得到160的结果，<code>println</code>函数打印结果为 ;160  </p>
<p>但是这个打印的功能只是附加的，每个函数都要求值才是repl本身就有的，<code>println</code> 求值为空，</p>
<p>即<code>nil</code> </p>
', 'caiyuyu', '', 'http://localhost/templates/business/caiyuyu/videos/【哆啦A梦伴我同行】只愿你留在我身边.mp4', NULL, NULL, 'document', '2017-12-29 00:17:54.726', '学习笔记');
COMMIT;

-- ----------------------------
-- Table structure for retrieve_account_tokens
-- ----------------------------
DROP TABLE IF EXISTS "retrieve_account_tokens";
CREATE TABLE "retrieve_account_tokens" (
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "token" varchar(255) COLLATE "pg_catalog"."default",
  "create_at" timestamp(6)
)
;
ALTER TABLE "retrieve_account_tokens" OWNER TO "postgres";

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS "tags";
CREATE TABLE "tags" (
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "count" int4,
  "account" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "tags" OWNER TO "postgres";

-- ----------------------------
-- Records of tags
-- ----------------------------
BEGIN;
INSERT INTO "tags" VALUES ('にじげん', 1, 'caiyuyu');
INSERT INTO "tags" VALUES ('clojure', 2, 'caiyuyu');
COMMIT;

-- ----------------------------
-- Table structure for theme
-- ----------------------------
DROP TABLE IF EXISTS "theme";
CREATE TABLE "theme" (
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "belong" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "theme" OWNER TO "postgres";

-- ----------------------------
-- Records of theme
-- ----------------------------
BEGIN;
INSERT INTO "theme" VALUES ('libra', 'caiyuyu', 'business/caiyuyu/blog/libra');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "nickname" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "describe_md" text COLLATE "pg_catalog"."default",
  "domain" varchar(255) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "disqus" varchar(255) COLLATE "pg_catalog"."default",
  "privilege" int4,
  "theme" varchar(255) COLLATE "pg_catalog"."default",
  "mind" varchar(255) COLLATE "pg_catalog"."default",
  "describe_html" text COLLATE "pg_catalog"."default",
  "disqus_access_token" varchar(255) COLLATE "pg_catalog"."default",
  "analysis" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "blog_subject" varchar(255) COLLATE "pg_catalog"."default",
  "blog_describe" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "disqus_apikey" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "users" OWNER TO "postgres";

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO "users" VALUES ('caiyuyu', 'pbkdf2+sha256$8cf6e3f498dabe1aa4d2f4ff$100000$8382d1ac8234d32a9f197ebd8507d0943d16183b', 'caiyuyu', 'callmecaiyuyu@gmail.com', '>大家好，我是蔡宇煜，正如你所见，是一只码农。

>简简单单的世界，简简单单的我。

>一直以来都是作为一个小人物而存在，既不是焦点，也平淡无奇，但是我依然活得很好。

>若生为林木，我当欣欣以向荣；若生为幽草，我当萋萋而摇绿。

>这是我想做的，也是正在做的，愿你我共勉！

>联系邮箱：callmecaiyuyu@gmail.com

>[个人博客](http://blog.caiyuyu.net)', 'undefined', NULL, '', NULL, 'libra', '啦啦啦，我是蔡宇煜', '<blockquote>
<p>大家好，我是蔡宇煜，正如你所见，是一只码农。</p>
<p>简简单单的世界，简简单单的我。</p>
<p>一直以来都是作为一个小人物而存在，既不是焦点，也平淡无奇，但是我依然活得很好。</p>
<p>若生为林木，我当欣欣以向荣；若生为幽草，我当萋萋而摇绿。</p>
<p>这是我想做的，也是正在做的，愿你我共勉！</p>
<p>联系邮箱：callmecaiyuyu@gmail.com</p>
<p><a href="http://blog.caiyuyu.net">个人博客</a></p>
</blockquote>
', '', '', '', '程序员创造世界', '', '蔡宇煜', '');
COMMIT;

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS "videos";
CREATE TABLE "videos" (
  "filename" varchar(255) COLLATE "pg_catalog"."default",
  "account" varchar(255) COLLATE "pg_catalog"."default",
  "id" varchar(255) COLLATE "pg_catalog"."default",
  "postid" varchar(255) COLLATE "pg_catalog"."default",
  "time" timestamp(6),
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "videos" OWNER TO "postgres";

-- ----------------------------
-- Records of videos
-- ----------------------------
BEGIN;
INSERT INTO "videos" VALUES ('【哆啦A梦伴我同行】只愿你留在我身边.mp4', 'caiyuyu', '/templates/business/caiyuyu/videos/【哆啦A梦伴我同行】只愿你留在我身边.mp4', NULL, '2017-12-29 00:12:23.034', 'video');
COMMIT;
