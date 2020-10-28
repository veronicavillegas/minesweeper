# MINESWEEPER
## Getting Started

The objective is to use Heroku as a server but at this moment I have problems between memcached and heroku.
 
The way to init the project is cloning the repo and then:

```
mvn clean install
mvn exec:java
```
Application starts listen on http://localhost:4567.

## Usage

#### Creating game board.

> [POST] /create_playing_board

Body:

```
{
    "columns":count_of_columns,
    "rows":count_of_rows,
    "mines":count_of_mines,
    "user":"your_username"
}
```

Response:

```
{
    "statusResponse": {
        "status": 201,
        "message": "OK"
    },
    "playingBoard": {
        "board": [
            [
                {
                    "display": "BLANK",
                    "value": 0,
                    "row": 0,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 0,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 0,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 0,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 1,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 3,
                    "row": 1,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 2,
                    "row": 1,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 2,
                    "row": 1,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 2,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 3,
                    "row": 2,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 2,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 2,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 3,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 3,
                    "row": 3,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 3,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 3,
                    "column": 3
                }
            ]
        ],
        "playStatus": "INIT",
        "id": boardId
    }
}
```


#### Playing
* You should use the id obtained in the previous step and set row and column you want to discover. 
* You should indicate if you want to discover or only mark the cell. If you don't say anything, I will assume you want to discover the cell.
* Property "playStatus" will show you the actual state of your game: INIT, CONTINUE, GAME_OVER
* The application will understand that if you don't send 'selectedCell' you want to resume an old game indicated by id "id".

> [POST] /play

Body:
```
{
    "selectedCell": {
        "row": 0,
        "column": 2
    },
    "action": "discover" / "mark"
    "id": boardId
}
```
Response
```
{
    "statusResponse": {
        "status": 201,
        "message": "OK"
    },
    "playingBoard": {
        "playStatus": "CONTINUE",
        "id": "vero2020-10-27T18:58:24.229"
        "board": [
            [
                {
                    "display": "DISCOVERED",
                    "value": 0,
                    "row": 0,
                    "column": 0
                },
                {
                    "display": "DISCOVERED",
                    "value": 0,
                    "row": 0,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 0,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 0,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 1,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 1,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 1,
                    "row": 1,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 1,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 2,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 2,
                    "row": 2,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 3,
                    "row": 2,
                    "column": 2
                },
                {
                    "display": "DISCOVERED",
                    "value": 2,
                    "row": 2,
                    "column": 3
                }
            ],
            [
                {
                    "display": "BLANK",
                    "value": 2,
                    "row": 3,
                    "column": 0
                },
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 3,
                    "column": 1
                },
                {
                    "display": "BLANK",
                    "value": 2,
                    "row": 3,
                    "column": 2
                },
                {
                    "display": "BLANK",
                    "value": 9,
                    "row": 3,
                    "column": 3
                }
            ]
        ]
    }
}
```
## Notes
* For this project I use memcached technology due to I consider the data involved is not critical. 
It will keep your game for 24 hours.
* I recognize this project needs improvements for example:
    * Unit tests: I consider it is an important point but in this case It was easy for me to test the little flows each time. 
    * I would like to offer you the list of games so you can select anyone you want.
    * Traking time of the game.
## Questions

[veronicayvill@gmail.com](veronicayvill@gmail.com)

