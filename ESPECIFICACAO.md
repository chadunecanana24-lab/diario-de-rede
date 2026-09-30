# Especificação do Projecto — Diário de Rede

**Universidade São Tomás de Moçambique (USTM)**
Curso de Administração e Sistemas de Informação e Redes
Turma 3L6LASIR 3T — Tema C
Projecto Prático: Desenvolvimento de Aplicação Android

**Discentes:** Abrão Chiau; Chadun Assane Canana; Henriques Lopes; Eddy
**Data:** 30 de Setembro de 2026

---

## 1. Introdução

O presente documento apresenta a especificação da aplicação **Diário de Rede**, desenvolvida em Java para a plataforma Android no âmbito do Projecto Prático. A aplicação responde ao Tema C, que requer a identificação do estado da ligação de rede do dispositivo e o registo de notas manuais com data e hora automáticas.

O documento descreve o objectivo, os requisitos, a estrutura das Activities, a navegação, a funcionalidade de rede, as permissões, o plano de testes e as limitações do trabalho.

## 2. Objectivo da aplicação

**Objectivo geral:** permitir ao utilizador conhecer o estado da ligação de rede do seu dispositivo e manter um registo cronológico de notas relacionadas com a rede.

**Objectivos específicos:**

1. Identificar, com recurso à API real do Android, se o dispositivo está ligado por Wi-Fi, por dados móveis ou sem ligação.
2. Actualizar essa informação automaticamente quando a rede muda.
3. Registar notas manuais com data e hora atribuídas automaticamente pelo sistema.
4. Apresentar as notas numa lista e preservá-las entre sessões.
5. Aplicar os conceitos abordados nas aulas: ConstraintLayout, Widgets, Activities, Intent e permissões.

## 3. Requisitos

### 3.1 Requisitos funcionais

| Código | Requisito |
|--------|-----------|
| RF01 | A aplicação deve apresentar o estado da ligação: Wi-Fi, Dados móveis ou Sem ligação. |
| RF02 | O estado deve ser actualizado automaticamente quando a rede se altera. |
| RF03 | O utilizador deve poder escrever e guardar uma nota manual. |
| RF04 | Cada nota deve ser guardada com a data e hora automáticas do registo. |
| RF05 | Cada nota deve incluir o estado da rede no momento do registo. |
| RF06 | As notas devem ser apresentadas numa lista, da mais recente para a mais antiga. |
| RF07 | Não deve ser possível guardar uma nota vazia. |
| RF08 | As notas devem permanecer disponíveis após fechar a aplicação. |

### 3.2 Requisitos não funcionais

| Código | Requisito |
|--------|-----------|
| RNF01 | Interface organizada com ConstraintLayout. |
| RNF02 | Compatibilidade com Android 7.0 (API 24) ou superior. |
| RNF03 | Utilização apenas da permissão estritamente necessária (`ACCESS_NETWORK_STATE`). |
| RNF04 | Textos da interface definidos em `strings.xml`. |

## 4. Descrição das funcionalidades

| Funcionalidade | Descrição |
|----------------|-----------|
| Estado da rede | Apresenta "Wi-Fi", "Dados móveis" ou "Sem ligação", com cor verde, laranja ou vermelha, respectivamente. |
| Registo de nota | O utilizador escreve o texto e prime **Guardar nota**. Se o campo estiver vazio, é apresentada uma mensagem de erro. |
| Data e hora automáticas | Obtidas do sistema no momento do registo, no formato `dd/MM/yyyy HH:mm:ss`. |
| Lista de notas | Cada item mostra data, estado da rede e texto. Sem registos, surge a mensagem "Ainda não há notas guardadas". |
| Persistência | As notas são guardadas em `SharedPreferences`, em formato JSON. |

## 5. Descrição das Activities

### 5.1 MainActivity (`activity_main.xml`)

Activity inicial. Contém:

- `TextView` com o estado da ligação;
- `EditText` para escrever a nota;
- `Button` **Guardar nota**;
- `Button` **Ver notas**, que abre a segunda Activity.

Em `onStart()` regista um `NetworkCallback` para receber alterações da rede; em `onStop()` remove esse registo, evitando consumo desnecessário de recursos.

### 5.2 NotasActivity (`activity_notas.xml`)

Segunda Activity. Contém:

- `ListView` com as notas, alimentada por um `ArrayAdapter`;
- `TextView` apresentado quando a lista está vazia;
- `Button` **Voltar**, que termina a Activity.

### 5.3 Classe de apoio NotasStore

Classe utilitária responsável por guardar e ler as notas. Cada nota é um objecto JSON com os campos `data`, `estado` e `texto`.

Ambos os layouts utilizam **ConstraintLayout**, com as constraints definidas em relação ao contentor pai e entre os componentes.

## 6. Navegação entre as Activities

```
MainActivity ──(Intent explícito: botão "Ver notas")──► NotasActivity
MainActivity ◄──(finish(): botão "Voltar" ou tecla Back)── NotasActivity
```

A ligação é feita com o código:

```java
startActivity(new Intent(MainActivity.this, NotasActivity.class));
```

## 7. Funcionalidade de rede utilizada

A aplicação usa a API nativa de estado da ligação do Android:

| Elemento | Função |
|----------|--------|
| `ConnectivityManager.getActiveNetwork()` | Obtém a rede activa. Se for `null`, o estado é "Sem ligação". |
| `ConnectivityManager.getNetworkCapabilities()` | Obtém as capacidades da rede activa. |
| `NetworkCapabilities.hasTransport(TRANSPORT_WIFI)` | Identifica ligação Wi-Fi. |
| `NetworkCapabilities.hasTransport(TRANSPORT_CELLULAR)` | Identifica dados móveis. |
| `registerDefaultNetworkCallback()` | Notifica a aplicação quando a rede muda (`onAvailable`, `onLost`, `onCapabilitiesChanged`). |

Trata-se de uma funcionalidade de rede real, que reflecte o estado efectivo do dispositivo.

## 8. Permissões necessárias

| Permissão | Tipo | Justificação |
|-----------|------|--------------|
| `android.permission.ACCESS_NETWORK_STATE` | Normal | Necessária para utilizar o `ConnectivityManager`. Declarada no `AndroidManifest.xml` e concedida na instalação; não requer pedido em tempo de execução. |

Não são utilizadas permissões de localização, Internet ou armazenamento externo, cumprindo o princípio do menor privilégio.

## 9. Informação relevante para a execução

- **Ambiente:** Android Studio; linguagem Java.
- **Versão mínima:** Android 7.0 (API 24), exigida por `registerDefaultNetworkCallback`.
- **Ligação à Internet:** não é necessária; a aplicação apenas consulta o estado da rede.
- **Execução:** abrir a pasta do projecto no Android Studio, aguardar o Gradle Sync e executar em telemóvel ou emulador (instruções completas no `README.md`).

## 10. Plano de testes e evidências

| Teste | Procedimento | Resultado esperado | Evidência |
|-------|--------------|--------------------|-----------|
| T1 | Ligar ao Wi-Fi e abrir a aplicação | Estado "Wi-Fi" a verde | `docs/screenshots/01_wifi.png` |
| T2 | Desligar o Wi-Fi e activar dados móveis | Estado "Dados móveis" a laranja | `docs/screenshots/02_dados_moveis.png` |
| T3 | Activar o modo avião | Estado "Sem ligação" a vermelho | `docs/screenshots/03_sem_ligacao.png` |
| T4 | Escrever e guardar uma nota | Mensagem "Nota guardada com sucesso" | `docs/screenshots/04_guardar_nota.png` |
| T5 | Abrir "Ver notas" | Nota listada com data, hora e estado | `docs/screenshots/05_lista_notas.png` |
| T6 | Guardar com o campo vazio | Mensagem de erro; nada é guardado | — |
| T7 | Fechar e reabrir a aplicação | As notas continuam na lista | — |

## 11. Limitações e trabalho futuro

**Limitações:** as notas não podem ser editadas nem apagadas individualmente; o estado indica apenas o tipo de ligação, não a qualidade nem a velocidade.

**Trabalho futuro:** remoção e edição de notas; migração do armazenamento para SQLite/Room; exportação do diário; medição da qualidade da ligação.

## 12. Conclusão

A aplicação **Diário de Rede** cumpre os requisitos do Tema C e os conceitos obrigatórios do projecto: utiliza ConstraintLayout e Widgets, possui duas Activities ligadas por Intent, implementa uma funcionalidade de rede real e utiliza correctamente a permissão necessária. O trabalho permitiu consolidar conhecimentos sobre o ciclo de vida das Activities, a comunicação entre componentes e o acesso aos serviços do sistema Android.

## 13. Referências

- Android Developers. *ConnectivityManager*. https://developer.android.com/reference/android/net/ConnectivityManager
- Android Developers. *NetworkCapabilities*. https://developer.android.com/reference/android/net/NetworkCapabilities
- Android Developers. *Intents and intent filters*. https://developer.android.com/guide/components/intents-filters
- Android Developers. *Build a responsive UI with ConstraintLayout*. https://developer.android.com/develop/ui/views/layout/constraint-layout
- Android Developers. *Permissions on Android*. https://developer.android.com/guide/topics/permissions/overview
- Android Developers. *Save key-value data (SharedPreferences)*. https://developer.android.com/training/data-storage/shared-preferences
