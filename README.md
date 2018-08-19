<h1 >P-MVP：基于 dagger.android 的 MVP 框架 </h1>

<p>
   <a href="https://bintray.com/passin95/maven/P-MVP/0.0.3">
    <img src="https://img.shields.io/badge/Jcenter-v0.0.3-brightgreen.svg?style=flat-square" alt="Latest Stable Version" />
  </a>
</p>


## Difference

- 将所有的 activity、fragment、dialog 都看成一个个 page，以 page 为单位使用定义 P 和 M 层的@Scope（生命周期），相对来说很自由的复用 P 层和 M 层。
- 不需要重复在每一个 V 层重复编写 Module 注入的代码。
- 由于 Retrofit 已经做了一层接口封装，并且 M 层大部分都是网络请求，因此去掉了 M 层和 P 层的接口，可针对数据库单独写一个 M 层。
- 删除了 ImageLoader 相关，个人觉得过于鸡肋，针对 App 功能的定位便应该决定好图片加载框架的选择，此外，基础框架本身的扩展性足够强，中间层设计就显得多此一举，甚至丧失了原有框架的良好特性。
- 业务包名建议以界面为单位，方便同一界面类之间的查找和归类，如果需求需要删除某个界面，也只需要右键删除包名即可删除该界面的所有有关类。
- 集成了 Fragmentation 去尽量避免 fragment 存在的坑，且方便管理和查看 fragment 栈，个人推荐对 fragment 的使用更多在于针对“碎片”的需求亦或某个很重要的界面需要比较快的启动速度，其余时候依旧推荐使用 activity。
- 出于性能优化和实用性的考虑，取消了 activity 和 fragment 的生命周期代理类，因为 android sdk 已提供 activity 和 fragment 的生命周期回调 Api，已可在回调中进行想要的操作。

## Explain
- 不在 BaseActivity 中通过泛型生成 P 层的原因是，我某些时候希望我在不使用 P 层的时候依旧使用 dagger 注入的功能。
- 在对比了实用性以及目前的维护情况等多方面因素后，最终选择了严振杰老哥的 AndPermission，碰到有关权限问题也推荐大家直接向严振杰老哥提 PR，为国产手机权限问题做贡献。
- 给大家安利两种不错的屏幕适配方法，[**第一种方式（sw）**](https://blog.csdn.net/fesdgasdgasdg/article/details/78108169)相对完美，但会使 apk 大小增加几百 k,并且无法适配第三方activity；[**第二种方式**](https://www.jianshu.com/p/d09a8961d6ec) 为今日头条适配方式的衍生版，已在 ScreenUtils 中提供，大家可根据实际情况选择使用。
- 为了方便开发，根目录也提供了本人在组件化项目中使用的页面 tempelate，需要的可以参考一下根据自身需求修改。

## Download

```
implementation 'me.passin:pmvp:0.0.3'
```

## Thanks

* [**MVPArms**](https://github.com/JessYanCoding/MVPArms)
* [**Fragmentation**](https://github.com/YoKeyword/Fragmentation)
* [**AndPermission**](https://github.com/yanzhenjie/AndPermission)
* ……


## License
``` 
 Copyright 2018, passin
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
