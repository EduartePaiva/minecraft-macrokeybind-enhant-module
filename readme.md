# Macro Key bind Minecraft 1.8 módulo de encantamento

## Requisitos
Módulo testado na versão 1.8 do minecraft, em específico no SLEEP ModPack

## instalação

- baixe o arquivo [baixar última versão](https://github.com/EduartePaiva/minecraft-macrokeybind-enhant-module/releases/latest)
- coloque na pasta ``liteconfig/common/macros/modules``.  
- Se não encontrar esta pasta modules ela está sempre na pasta de macros, onde seus macros são salvos.

## Utilizando

Após colocar este arquivo na pasta de macros/modules, o mod macro key bind ganhou a função ``enchant(numero)`` executar ``enchant(1)`` clica na primeira opção da mesa de encantamento, ``enchant(2)`` segunda opção e ``enchant(3)`` terceira opção, com isso um leque grande de automatizações se abrem, exemplos são encantar quando atingir nível 30 em atividades AFK como matar mobs ou pesca.

## Faça seu próprio build

O código é open source, se quizer fazer alguma alteração ou buildar do código fonte clone o repositório, use o gradle na versão 4.9 e java jdk 8 para compilar.

```bash
gradle wrapper --gradle-version 4.9

.\gradlew.bat build
```

Compilação utilizando o docker-compose:

```bash
docker compose run --rm builder
```

arquivo se encontra em ``build/libs/``