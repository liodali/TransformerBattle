# TransformerWar
> Android application build it  with kotlin that illustrate battle between autobots and decepticon


##### In this project, we implement the  clean architecture
* we have 3 layer:

  * <srong>App module </string>  : This module contains all of the code related to the UI/Presentation layer such as activities,fragment,dialog,custom views  and contain viewmodel,dependency injection module app 
  * <srong>Core</string> : holds all concrete implementations of our repositories,usecaes and other data sources like  network
  * <srong>Domain module </string>  : contain all interfaces of repositories ,usecase and data classes



> we used hilt as dependency injection for this project and retorfit to make http calls

### screenshots
<img width="150" height="300" src="/screenshot/listTransformers.png"  alt="list of transformers"> <img width="150" height="300" src="/screenshot/showTeamSelector.png" alt="popup to select team of new transformer"> <img width="150" height="300" src="/screenshot/autobotsFormCreate.png" alt="game stated">
<img width="150" height="300" src="/screenshot/decepticonFormCreaion.png" alt="game was terminated"> <img width="150" height="300" src="/screenshot/battleStarted.png" alt="game stated"> <img width="150" height="300" src="/screenshot/gameFinished.png" alt="game was terminated"> 