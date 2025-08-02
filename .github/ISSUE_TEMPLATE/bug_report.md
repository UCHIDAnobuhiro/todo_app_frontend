name: 🐛 バグ報告
description: アプリの不具合を報告するためのテンプレート
title: "[Bug] "
labels: ["bug"]
assignees: []

body:
- type: textarea
  id: overview
  attributes:
  label: 概要
  description: どのようなバグが発生したかを簡潔に書いてください
  placeholder: 例）タスクを追加しても画面に表示されない
  validations:
  required: true

- type: textarea
  id: steps
  attributes:
  label: 再現手順
  description: バグが発生するまでの手順をできるだけ詳しく書いてください
  placeholder: |
  1. アプリを起動する
  2. タスクを追加する
  3. リスト画面に戻る
  4. タスクが表示されない
  validations:
  required: true

- type: textarea
  id: expected
  attributes:
  label: 本来の期待動作
  placeholder: 例）追加したタスクがリストに表示されるべき

- type: textarea
  id: env
  attributes:
  label: 環境情報
  placeholder: |
  - Android Studio Giraffe
  - Pixel 6 Emulator
  - API 33