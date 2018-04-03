<h1 align="center">P-MVP</h1>
<h2 align="center">基于dagger.android的组件化Demo</h2>

## Directory

<img src="https://github.com/passin95/P-MVP/blob/master/image/module_relationship%20.png" width="80%" height="80%">


        - app                整个项目的最顶层，包含着所有业务Module，也是所有业务Module的跳转开关。
        - module_业务        根据业务划分Module，对于一些全局化的配置建议在app_global_config中配置，
                             因为在Module业务设置全局配置其他Module也会生效（或覆盖app_global_config已配置好的属性），
                             会给其它Module带来不确定性，该Module对ModuleConfig的实现的作用更应该是针对本身Module进行约束操作。
        - module_view        对于一些体量较大的控件（界面）或功能可单独抽为Module，例如大厂的搜索功能。
        - app_global_config  app的全局配置，以及一些全局使用的类库（往往带有一些可变性）应在此依赖。
        - pmvp               可理解为Framework层，整个APP架构底层所在，对外暴露接口或方式对APP进行配置，
                             该层与app_global_config的最大区别在于，pmvp的代码往往是必不可少的。

## Notice

- 请在local.properties下添加（true代表以该Module作为Application编译）：


        app = true
        module_example = false
        module_fragmentation = false
        local.properties不会被add到仓库，方便协作，
        如果没有此需求，可按需对cc-setttings.gradle修改。
        
- 该想更换组件通信方式的自行按需求更换，该Demo的目的主要为大家提供组件化思路。
- 该Demo从开始尝试（3月13日）到至今（不断的学习、尝试、修改）时间较短，因此还不够完善，但大概思路已实现。
- 思路都在代码里面，**请阅读源码**，**请阅读源码**，**请阅读源码**，重要的事情说三遍！！！
- [**更多组件通信方式的使用请移步CC**](https://github.com/luckybilly/CC)
- [**更多fragment的使用请移步Fragmentation**](https://github.com/YoKeyword/Fragmentation)


## Thanks
* [**MVPArms**](https://github.com/JessYanCoding/MVPArms)
* [**CC**](https://github.com/luckybilly/CC)
* [**Fragmentation**](https://github.com/YoKeyword/Fragmentation)

## Speech
* **如果该Deme给您带来了帮助，请您点击右上角 Star 支持一下谢谢!**
* **如果该Deme给您带来了帮助，请您点击右上角 Star 支持一下谢谢!**
* **如果该Deme给您带来了帮助，请您点击右上角 Star 支持一下谢谢!**

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
