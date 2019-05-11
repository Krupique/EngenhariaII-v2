# EngenhariaII-v2 - BeduSystem v2 (Com a interface padronizada e bonita)

### Importante:
  * Não tente iniciar o Sistema sem cadastrar a Parametrização: Eu acho que pode dar pau na hora de carregar o logo (Não testei).
  * Todas as classes que estão nos pacotes entidades e controladoras, eu só copiei e colei no dia 09/05/2019.
  * Eu precisei criar quatro métodos nas classes Parametrização e CtrParametrização (dois em cada classe). São eles:

**Parametrização:**
  * public String corParam()
  * public BufferedImage[] imgParam()

**CtrParametrização:**
  * public String corParamatrizacao()
  * public BufferedImage[] imgParametrizacao()


### Observações:
  **Eu separei todas as interfaces nos pacotes:**
  * **inicio:** NÃO acho que alguém vá mexer nessas aqui.
  * **interfaces/basicas:** Funções básicas.
  * **interfaces/fundamentais:** Funções fundamentais.
  * **interfaces/buscas:** Funções de buscas das básicas.
  * **interfaces/relatorios:** Funções de relatorios.
  Caso você implemente as buscas direto na tela de cadastro e não utilize a tela separada, favor deletar para não poluir o sistema com telas inutilizadas.

  **As seguintes telas eu não criei nenhum FXML:**
  * FXMLStatus.fxml

  **Ignorar quando achar esse comentário**
  > //################################# PARTE DE ESTILO DA TELA #################################//

  **Aqui estão as funções que cada dos botões, etc**
  > //################################# PARTE DE FUNCIONALIDADES DA TELA #################################//

  **Aqui estão os códigos da versão antiga que na hora da implementação da nova eu não entendi para que serve ou não acho que deva ser útil, mas cada um ve e analisa se serve ou não para alguma coisa.**
  > //################################# PARTES DO SISTEMA QUE NÃO SEI SE AINDA É ÚTIL #################################//


### Utilidades: 
  A classe CorSistema é uma classe com a cor padrão do sistema (verde) ou a cor que foi cadastrada na parametrização.

  **Para setar os JFXTextField com a cor do sistema use a seguinte função:**
  * txt_nome_textfield.setFocusColor(CorSistema.hex2Rgb(CorSistema.getCorHex()));

  **Para setar a cor de algum botão use:**
  * String cor = CorSistema.getCorHex();
  * btBotao.setStyle("-fx-background-color: " + cor);


### Dados da tela:
  **Largura total:** 1080
  **Altura total:** 680

  **Largura da parte funcional:** 880
  **Altura da parte funcional:** 680


### Cores:
  * **Cor padrão:**		#347E65	(Verde)
  * **Fundo:**  			#EAEAEA (Cinza bem claro)
  * **Panes top, bot:**	#D1D1D1 (Cinza mais escuro)


#### Ignorar estes números:
  Larg
  880
  185 x 3 = 555
  81 - 347 - 613

  Alt
  680
  195 * 3 = 585
  23 - 241 - 459

