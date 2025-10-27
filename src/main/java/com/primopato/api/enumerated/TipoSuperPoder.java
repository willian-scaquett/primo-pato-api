package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoSuperPoder {
    //Blindemos o drone da água. Risco baixo, pois as tecnologias para se blindar esquipamentos
    //eletrônicos de água são conhecidas. Um pato tacando água é estranho, mas nada que ofereça
    //grandes ganhos em conhecimento paranormal.
    AGUA("Água", DefesaDrone.REVESTIMENTO_HIDROFOBICO, 5f, 20f),
    //Melhor proteger o drone das chamas. Risco aumenta, pois pode derreter alguns equipamentos ou
    //sujar sensores com fuligem. Ganho paranormal aumenta, mas ainda é baixo porque o fogo ainda é
    //um elemento da natureza.
    FOGO("Fogo", DefesaDrone.CERAMICA_REFRATARIA, 7f, 30f),
    //Isolemos o drone da eletricidade externa, para evitar curtos circuitos. Risco aumenta pela chance de
    //algum componente eletrônico ser afetado. Ganho paranormal baixo, pois animais controlando eletricidade
    //já é algo conhecido pela ciência.
    ELETRICIDADE("Eletricidade", DefesaDrone.ISOLAMENTO_GRAFENO, 3f, 40f),
    //Um visão de calor ou algo do gênero? Temos um regulador de temperatura que conseguirá proteger o drone
    //das mais elevadas temperaturas. Risco aumenta pela chance de componentes eletrônicos serem derretidos.
    //Ganho paranormal aumenta, pois visão de valor só existe em HQs.
    CALOR("Calor", DefesaDrone.CAMPO_TERMORREGULADOR_ADAPTATIVO, 10f, 40f),
    //Se o bicho é rápido, precisamos prever onde ele estará para evitar seus ataques. Risco aumenta, pois
    //será díficil acertar os ataques e ele deve bater com força (f = m x a). Ganho paranormal baixo, porque
    //velocidade elevada em animais é algo conhecido pela ciência.
    VELOCIDADE("Velocidade", DefesaDrone.RADAR_INERCIAL_PREVISIVO, 4f, 50f),
    //Principio de defesa parecido com a velocidade, a diferença é que agora precisamos descobrir quando o
    //pato abrirá fendas no espaço-tempo. Risco alto por ser difícil acertá-lo e fácil de sofrer ataques.
    //Ganho paranormal alto. Onde você já viu um pato abrindo fendas quânticas?
    TELETRANSPORTE("Teletransporte", DefesaDrone.SENSOR_QUANTICO_FENDAS, 25f, 50f),
    //Como um ataque psíquico afetaria uma máquina sem psique? O casco padrão do drone aguenta o serviço.
    //O risco é nulo pelo mesmo motivo. Ganho paranormal alto, pois o cara é o Professor Xavier dos patos.
    PSIQUICO("Psíquico", DefesaDrone.NENHUMA, 30f, 0f),
    //Mesmo princípio da água benta: se funciona com vampiros, funcionará com patos assombração. Risco
    //baixo, pois drones não tem alma para ser assombradas. Ganho paranormal altíssimo. É UM PATO ASSOMBRAÇÃO!
    SOBRENATURAL("Sobrenatural", DefesaDrone.ALHO, 50f, 10f),
    //Se não sabemos o tipo do poder dele, torcemos para nossa IA descobrir minimamente como. Risco altíssimo,
    //pois não sabemos o que está por vir. Ganho paranormal considerável, pois se trata de um tipo de poder diferente
    //dos conhecidos até então.
    OUTRO("Outro", DefesaDrone.ESCUDO_ADAPTATIVO_IA, 20f, 75f);

    private final String nome;
    private final DefesaDrone defesa;
    private final Float ganhoParanormalBase;
    private final Float risco;
}
