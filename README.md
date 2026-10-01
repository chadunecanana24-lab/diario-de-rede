# Diário de Rede

> Aplicação Android desenvolvida em Java para monitorização do estado da ligação de rede e registo de notas com data e hora automáticas.

## Ficha do trabalho

| Campo | Informação |
|-------|------------|
| **Instituição** | Universidade São Tomás de Moçambique (USTM) |
| **Curso** | Administração e Sistemas de Informação e Redes |
| **Turma** | 3L6LASIR 2T |
| **Tema** | Tema C — Diário de Rede |
| **Trabalho** | Projecto Prático — Desenvolvimento de Aplicação Android |
| **Linguagem** | Java |
| **Data** | 30 de Setembro de 2026 |

## Integrantes do grupo

| N.º | Nome completo |
|-----|---------------|
| 1 | Abrão Chiau |
| 2 | Chadun Assane Canana |
| 3 | Henriques Lopes |
| 4 | Jeremias Fernando Chichava  |

## 1. Descrição da aplicação

O **Diário de Rede** é uma aplicação móvel para o sistema operativo Android que identifica, em tempo real, o estado da ligação de rede do dispositivo — **Wi-Fi**, **Dados móveis** ou **Sem ligação** — e permite ao utilizador registar notas manuais associadas à rede. Cada nota é armazenada com a **data e hora automáticas** do registo e com o estado da rede verificado nesse instante, sendo apresentada numa lista ordenada da mais recente para a mais antiga.

A aplicação permite, por exemplo, documentar quedas de ligação, lentidão ou a qualidade da rede em diferentes locais e momentos, constituindo um registo cronológico consultável pelo utilizador.

## 2. Funcionalidades implementadas

- Identificação do estado actual da ligação: Wi-Fi, Dados móveis ou Sem ligação.
- Actualização automática do estado sempre que a rede se altera (`NetworkCallback`).
- Indicação visual do estado por cor (verde, laranja e vermelho).
- Registo de notas manuais, com validação de campo vazio.
- Atribuição automática da data e hora e do estado da rede a cada nota.
- Apresentação das notas numa lista, com mensagem informativa quando não existem registos.
- Persistência das notas entre sessões da aplicação.
- Navegação entre duas Activities através de `Intent` explícito.

## 3. Tecnologias utilizadas

| Tecnologia | Utilização |
|------------|------------|
| Java | Linguagem de programação |
| Android Studio / Android SDK (minSdk 24, targetSdk 34) | Ambiente de desenvolvimento |
| ConstraintLayout | Organização dos layouts |
| Widgets (`TextView`, `EditText`, `Button`, `ListView`) | Elementos da interface |
| `Intent` | Ligação entre as Activities |
| `ConnectivityManager`, `NetworkCapabilities`, `NetworkCallback` | Funcionalidade de rede |
| `SharedPreferences` e `org.json` | Armazenamento das notas |
| Git e GitHub | Controlo de versões e entrega |

## 4. Permissões utilizadas

| Permissão | Tipo | Justificação |
|-----------|------|--------------|
| `android.permission.ACCESS_NETWORK_STATE` | Normal | Permite consultar o estado e o tipo da ligação de rede. É concedida na instalação, pelo que não exige pedido ao utilizador em tempo de execução. |

## 5. Estrutura do projecto

```
DiarioDeRede/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/mz/ac/ustm/diariorede/
│       │   ├── MainActivity.java      (estado da rede e registo de notas)
│       │   ├── NotasActivity.java     (lista de notas)
│       │   └── NotasStore.java        (persistência das notas)
│       └── res/
│           ├── layout/activity_main.xml
│           ├── layout/activity_notas.xml
│           └── values/strings.xml
├── docs/screenshots/                   (evidências)
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
└── ESPECIFICACAO.md
```

## 6. Instruções para executar o projecto

**Requisitos:** Android Studio (versão recente) e um telemóvel Android 7.0 (API 24) ou superior, ou um emulador.

1. Clonar ou descarregar o repositório (`Code → Download ZIP` e extrair).
2. No Android Studio, seleccionar **File → Open** e escolher a pasta `DiarioDeRede`.
3. Aguardar a conclusão do *Gradle Sync*. Se o Android Studio propuser actualizar o *Android Gradle Plugin*, aceitar.
4. Ligar o telemóvel com a depuração USB activa (ou iniciar um emulador).
5. Clicar em **Run ▶**.
6. Para testar, alternar entre Wi-Fi, dados móveis e modo avião com a aplicação aberta.

## 7. Screenshots da aplicação

| Wi-Fi | Dados móveis | Sem ligação |
|:-----:|:------------:|:-----------:|
| ![Wi-Fi](docs/screenshots/01_wifi.png) | ![Dados móveis](docs/screenshots/02_dados_moveis.png) | ![Sem ligação](docs/screenshots/03_sem_ligacao.png) |

| Registo de nota | Lista de notas |
|:---------------:|:--------------:|
| ![Guardar nota](docs/screenshots/04_guardar_nota.png) | ![Lista de notas](docs/screenshots/05_lista_notas.png) |

## 8. Documentação

A especificação completa (requisitos, Activities, navegação, permissões, plano de testes e limitações) encontra-se em [ESPECIFICACAO.md](ESPECIFICACAO.md).
