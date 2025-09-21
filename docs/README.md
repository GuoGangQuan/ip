# Gloqi User Guide

Gloqi is a task‑manager chatbot that reads text commands, executes them, and stores tasks such as Todos, Deadlines, and Events.
On startup it shows a data‑loading message followed by a greeting before accepting commands.
![Mambo ChatBot Interface](https://guogangquan.github.io/ip/Ui.png)

> [!NOTE] Notes about command format:
> * Command are case insensitive.
> * Argument keywords such as "/by" "/from" "/to" are case sensitive 
> * Items in square brackets [] are optional.</br>
> e.g delete <task_number>[,<task_number>...] can be used as ```delete 1,2``` or ```delete 1```
> * Items in arrow brackets <> are must to have.</br>
> e.g unmark <task_number> must have item <task_number> 

## :scroll: Quick Command Overview
1. list — Display all tasks in a numbered list.</br>
Format: ```list```
2. todo — Create a Todo task with a description.</br>
Format: ```todo <description>```
3. deadline — Create a Deadline task with a description and a /by date-time.</br>
Format: ```deadline <description> /by <yyyy-MM-dd HHmm>```
4. event — Create an Event task with a description, /from start and /to end date-times.</br>
Format: ```event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>```
5. mark — Mark the specified task number as done.</br>
Format: ```mark <task_number>```
6. unmark — Mark the specified task number as not done.</br>
Format: ```unmark <task_number>```
7. delete — Delete the specified task number(s).</br>
Format: ```delete <task_number>[,<task_number>...]```
8. show — List tasks that fall on a given date.</br>
Format: ```show <yyyy-MM-dd>```
9. find — Search tasks whose descriptions contain a keyword or phrase.</br>
Format: ```find <keyword or phrase>```
10. bye — Print the farewell message and exit.</br>
Format: ```bye```

## :notebook_with_decorative_cover:Detail Commands Description
### 1. List task: ```list```
---
Format: ```list```

Example: ```list```
### 2. Add todo task: ```todo```
---
Format: ```todo <description>```

Example: ```todo Assignment 3```
### 3. Add deadline task: ```deadline```
---
Format: ```deadline <description> /by <yyyy-MM-dd HHmm>```

Example: ```deadline Assignment 3 /by 2025-09-25 1800```
### 4. Add event task: ```event```
---
Format: ```event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>```

Example: ```event Lecture 3 /from 2025-09-25 1800 /to 2025-09-25 2000```
### 5. Mark task: ```mark```
---
Format: ```mark <task_number>```

Example: ```mark 3```
### 6. Unmark task: ```unmark```
---
Format: ```unmark <task_number>```

Example: ```unmark 3```
### 7. delete task(s): ```delete```
---
Format: ```delete <task_number>[,<task_number>...]```

Example: 
* Single task deletion: ```delete 1```
* Mass task deletion: ```delete 1,2,5,6```
### 8. Filter task base on date: ```show```
---
Format: ```show <yyyy-MM-dd>```

Example: ```show 2025-09-25```
### 9. Filter task base on task description: ```find```
---
Format: ```find <keyword or phrase>```

Example: ```find Assignment```
### 10. Exit: ```bye```
---
Format: ```bye```

Example: ```bye```

## :date: Date and Time Syntacts meaning:
| Syntacts                | Description            |
|-------------------------|------------------------|
| `yyyy`                  | Year in 4-digit        | 
| 'MM'                    | Month in 2-digit       |
| 'dd'                    | Day in 2-digit         |
| `HH`                    | Hours in 2-digit       | 
| `mm`                    | Minutes in 2-digit     |

## :information_source: Other info
1. Tasks are by default save at data/data.txt.
2. Save action is trigger every time you make modification to the task list.
