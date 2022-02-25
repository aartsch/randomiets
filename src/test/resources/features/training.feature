Feature: Lingo Trainer
  as a player
  i want to guess 5, 6 and 7 letter words
  in order to practice lingo

  Scenario: Start new game
    Given I haven't started a new game
    When  I start a new game
    Then  I need to guess a word with "5" letters
    And   the hint shows the first letter of the word
    And   the score is "0"

  Scenario Outline: Start new round
    Given I am playing a game
    And   I won the previous round
    And   the last word contains "<previous length>" letters
    When  I start a new round
    Then  the word contains "<next length>" letters

    Examples:
     | previous length | next length |
     | 5               | 6           |
     | 6               | 7           |
     | 7               | 5           |

  Scenario Outline: Guess a word
    Given I am playing a game
    And   the word to guess is "<word to guess>"
    When  I guess "<attempt>"
    Then  I get feedback "<feedback>

    Examples:
      | word to guess | attempt | feedback |
      |  GROEP        | GEGROET |  INVALID, INVALID, INVALID, INVALID, INVALID (te lang)
      |  GROEP        | GENEN   |  CORRECT, ABSENT, ABSENT, CORRECT, ABSENT
      |  GROEP        | GERST   |  CORRECT, PRESENT, PRESENT, ABSENT, ABSENT
      |  GROEP        | GEDOE   |  CORRECT, PRESENT, ABSENT, PRESENT, ABSENT
      |  GROEP        | GROEP   |  CORRECT, CORRECT, CORRECT, CORRECT, CORRECT






