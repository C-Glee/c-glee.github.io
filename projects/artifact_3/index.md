---
layout: page
title: "Contact Service"
permalink: /projects/artifact_3/
description: "A contact management service demonstrating an understanding of software design as well as UI and database design and implementation."
---

[Browse Original Artifact](https://github.com/C-Glee/c-glee.github.io/tree/main/projects/artifact_3/original)

[Browse Enhanced Artifact](https://github.com/C-Glee/c-glee.github.io/tree/main/projects/artifact_3/enhanced)

---

&nbsp;&nbsp;&nbsp;&nbsp; This artifact is the *ContactService* application from CS-320: Software Testing, Automation, and Quality Assurance and it was created in November of 2024. The original artifact was an exercise in creating JUnit tests. It consisted of two classes, *Contact* and *ContactService*, and a corresponding JUnit test for each of these classes. The original artifact does not offer any user interaction and its functionality is verified purely through its tests. This item was selected for a few reasons. The first reason is that I wanted to take a bare-bones project and flesh it out from a couple of non-interactive class files into an application which allows for a user to perform contact management. Taking a previous application from a very early phase and developing it into a full application helps to demonstrate the growth of my software design skills and understanding of algorithmic logic. The last reason was that *ContactService* is an app that necessitates a database and, before this enhancement, I had no experience with embedded databases. 

&nbsp;&nbsp;&nbsp;&nbsp; The initial enhancements for this artifact include methods which allows a user to perform CRUD operations on contacts, a GUI which enables the user’s interaction with the application, and an embedded database which persistently stores contact information. The enhancements demonstrate capability with designing and implementing a GUI using JavaFX, as well as designing a database schema and implementing an embedded database using SQLite. This process required research into methods for implementation of GUIs and embedded databases, weighing potential designs of both the GUI and database, and integrating that implementation into the design and logic of the application.

&nbsp;&nbsp;&nbsp;&nbsp; As creating a GUI and embedded database for a Java-based application were both relatively unfamiliar to me, the first step of the enhancement was creating a plan. I knew what I wanted to achieve and the next step was determining how to achieve it and weighing my options. The first challenge was determining which toolkit I wanted to use for my GUI. It came down to either JavaFX or Swing, and I settled on JavaFX since my research indicated it was more modern and actively being developed while Swing is established but generally used for legacy applications. After deciding to use JavaFX, the next step was to determine whether I was going to use SQLite or H2 for the embedded database. SQLite appeared to be widely used, robust, and resilient to corruption. The idea of H2’s pure java implementation was tempting, but after stumbling on other developers attempting to troubleshoot their H2 database corruption I decided to commit to SQLite. JavaFX and SQLite were both new to me, which presented a challenge in and of itself, but my experience with designing and implementing databases and UIs in the past made them both surprisingly intuitive. With the final enhancements completed, *ContactService* now allows for CRUD operations on its embedded Contact database through a fully interactive GUI.

&nbsp;&nbsp;&nbsp;&nbsp; The enhancements of this artifact successfully met all the intended course outcomes. The original intended outcomes were three, four, and five. The research related to the enhancement and the implementation of the enhancement demonstrates the capability of designing and evaluating computing solutions that solve a given problem using computer science practices and standards, as well as well-founded techniques, skills, and tools while weighing potential trade-offs. A security-oriented mindset is demonstrated both through comprehensive tests and through input validation of contact information.

---
