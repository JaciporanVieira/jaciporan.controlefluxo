# 🚀 Controle de Fluxo com Jetpack Compose

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green)
![Android](https://img.shields.io/badge/Android-Modern-blue)
![Architecture](https://img.shields.io/badge/Architecture-State--Driven-orange)

Aplicativo Android desenvolvido com foco em **Gerenciamento de Estado** e **Navegação Reativa** utilizando **Jetpack Compose**, simulando fluxos reais de autenticação com múltiplos perfis de usuário.

---

## 📑 Sumário

- [📝 Objetivo](#-objetivo-do-projeto)
- [🎯 Conceitos Aplicados](#-conceitos-aplicados)
- [🏗️ Decisão de Arquitetura](#️-decisão-de-arquitetura)
- [💡 Por que essa abordagem?](#-por-que-essa-abordagem)
- [🛠️ Tecnologias](#️-tecnologias-utilizadas)
- [🚦 Regras de Negócio](#-regras-de-negócio)
- [🧠 Fluxo de Estados](#-fluxo-de-estados)
- [📁 Estrutura de Projeto](#-estrutura-de-projeto)
- [👨‍💻 Autor](#-autor)

---

## 📝 Objetivo do Projeto

O objetivo foi implementar um fluxo de navegação **totalmente orientado por estado**, onde a UI responde automaticamente às mudanças de autenticação e perfil do usuário.

A aplicação simula três cenários principais:

- Usuário não autenticado
- Usuário comum
- Administrador

---

## 🎯 Conceitos Aplicados

- State Hoisting
- State-Driven UI
- Navegação condicional
- Reatividade com Compose
- Boas práticas de modelagem de estado
- Separação de responsabilidades

---

## 🏗️ Decisão de Arquitetura

### ✅ Opção escolhida: Sealed Class

```kotlin
sealed class UserState {
    object Loading : UserState()
    object NotLogged : UserState()
    object User : UserState()
    object Admin : UserState()
}
```
## 💡 Por que essa abordagem?

Diferente do uso de múltiplos booleanos (como `isLogged` ou `isAdmin`), essa estratégia baseada em estados:

* **✔ Evita estados inválidos:** O app nunca fica em dois estados simultâneos.
* **✔ Garante consistência:** A interface reflete fielmente o estado do dado.
* **✔ Facilita manutenção:** Lógica centralizada e fácil de rastrear.
* **✔ Melhora a legibilidade:** O código torna-se autodocumentado.
* **✔ Permite expansão futura:** Facilidade para adicionar novos níveis de acesso.

---

## 🛠️ Tecnologias Utilizadas

* **Kotlin**
* **Jetpack Compose**
* **Navigation Compose (v2.8.0)**
* **Version Catalog** (`libs.versions.toml`)
* **Gradle Kotlin DSL**

---

## 🚦 Regras de Negócio

### 🔄 Inicialização
* O app inicia em estado de `Loading` por **3 segundos**.
* Simula uma verificação real de autenticação ou busca de token.

### 🔐 Fluxo de Login
| Estado | Comportamento |
| :--- | :--- |
| **NotLogged** | Tela de login exibida |
| **User** | Navega automaticamente para Home |
| **Admin** | Navega automaticamente para Dashboard Admin |

### 🧭 Navegação
* **Global:** Totalmente controlada pelo estado do usuário.
* **Interna:** Gerenciada via `NavController` para sub-rotas.

### 🚪 Logout
* Reseta o estado global para `NotLogged`.
* O app retorna automaticamente para a tela de Login.
* A UI reage instantaneamente via **recomposição**.

---

## 🧠 Fluxo de Estados

```text
Loading (3s)
   │
   ▼
NotLogged (Login)
   │
   ▼
User / Admin (Home)
   │
   ▼
Navegação Interna
   │
   ▼
Logout ──────▶ NotLogged
```
---

## 📁 Estrutura de Projeto

```text
📦 app
 ┣ 📜 MainActivity.kt
 ┣ 📜 UserState.kt
 ┣ 📜 Navigation.kt
 ┣ 📂 Screens/
 ┃ ┣ 📜 LoginScreen.kt
 ┃ ┣ 📜 HomeScreen.kt
 ┃ ┣ 📜 AdminScreen.kt
 ┃ ┣ 📜 ProfileScreen.kt
 ┃ ┗ 📜 SettingsScreen.kt
 ┣ 📜 libs.versions.toml
 ┗ 📜 build.gradle.kts
```
---

## 👨‍💻 Autor

**Jaciporan Vieira**
* 💼 Desenvolvedor FullStack
* ☁️ Experiência com Cloud e Java

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/jaciporan-vieira-silva-483564158/)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat&logo=gmail&logoColor=white)](mailto:jaciporan@email.com)
