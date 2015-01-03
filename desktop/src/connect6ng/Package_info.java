/**
 * @brief 六子棋项目首页
 * @mainpage 六子棋介绍
 * @section 六子棋介绍
 * @subsection 游戏规则
 * 规则与五子棋非常相似，除了第一次黑方下一颗子外，之后黑白双方轮流每次各下
 * 两子，先连成六子者获胜。
 * 
 * 因为公平性不是问题，棋盘是可以任意地大，甚至是无限大亦可。 对一般玩家而
 * 言，采用围棋的十九路棋盘即可。
 * 
 * 对专业棋士而言，采用五十九路棋盘。专业棋士可用电脑玩五十九路棋盘；若不用电
 * 脑玩的话，也可以拿3×3个围棋棋盘合并起来玩。由于棋盘接合的线也算一路，这９
 * 个棋盘共形成五十九路的大棋盘。当然，在实际对局时，应先用一个围棋的十九路棋
 * 盘开始；当下超过棋盘时，才拿新的棋盘合并上去。
 * 
 * @subsection 公平性
 * 原则上，许多复杂的游戏如围棋及象棋等，也都无法宣称绝对公平。因此对公平性，
 * 只能先从几个角度来论证。
 * 
 * - 对六子棋来说，每当一方下出一步（两子）时，该方一定比对方多出一颗子。这
 *很自然地使得六子棋具有相当的公平性，不会偏向某个玩家。与五子棋相比，明显
 *地是较为公平的游戏。
 * - 目前发明者完成了一个六子棋程式。这个程式大都可以击败一般玩家。这里先由
 *电脑产生一千多种开局的样式，然后让电脑对电脑下。直到目前为止，还没有发现，
 *对某一方，特别有利。
 * - 另外，吴教授也证明了白方不能脱离战场，否则黑方胜。这个理论迫使双方必须
 *从中心点开始缠斗。若白方能成功脱离主战场，下到他处，则此游戏变成每人下两
 *颗子，而先的一方（这时变成白方）第一手下两颗子，这明显对白方有利。
 * 
 * 当然，公平性需要更多实战的经验，及一段时间的验证。
 * 
 * @subsection 复杂度
 *对六子棋而言，因为公平性不是问题，所以棋盘是可以任意地大，甚至是无限大亦
 *可。以上述的十九路棋盘为例，所谓状态空间复杂度可达\f$10^{172}\f$，与围棋相当。而博弈树
 *（Game tree）复杂度，亦可达\f$10^{140}\f$，远大于五子棋。
 * 
 *若用五十九路棋盘，则状态空间复杂度大过围棋，而博弈树复杂度也不亚于围棋。
 * 
 *另外值得一提的是五子棋的棋盘是15×15的原因是：Goro Sakata 及 Wataru Ikawa
 *两位专家提到愈大的棋盘，愈增加黑方获胜的机会，因此需要缩小棋盘大小。某些人
 *认为小棋盘，复杂度自然变小，让电脑更容易穷举出五子棋的胜负，但许多黑棋必胜
 *的变化因棋盘变小，反而变成双方可下的变化，所以复杂度是否变小还未有定论。而
 *改良后的五子棋加入了交换的开局规则，解决了为人诟病的公平性问题，成为可以使
 *用在专业比赛的竞技棋类。
 *
 * @section 开发文档说明
 * @subsection 开发概要
 * 本程序采用MVC架构，，整个程序被分成了如下几个模块：
 * 
 * - AI模块
 * - 数据管理模块
 * - 音效模块
 * - 历史记录模块
 * - 日志模块
 * - 程序说明模块
 * - 程序帮助模块
 * - 版本管理模块等。
 * 
 * 
 * 
 * @bug
 * - 游戏结束后，窗体会从前端撤出
 * - 执行历史记录相关的操作，会在用户目录下产生临时文件
 * 
 * @todo
 * - 网络对战部分
 * - 棋盘大小的改变
 * - 没有增加四连子的提示
 * - ……
 */

package connect6ng;
