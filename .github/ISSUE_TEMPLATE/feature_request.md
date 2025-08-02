name: 💡 機能提案
description: 追加してほしい機能・改善アイデアを提案
title: "[Feature] "
labels: ["enhancement"]
assignees: []

body:
- type: textarea
  id: summary
  attributes:
  label: 概要
  description: どんな機能・改善を求めているのか簡単に説明してください
  placeholder: 例）タスクに期限日を設定できるようにしたい
  validations:
  required: true

- type: textarea
  id: motivation
  attributes:
  label: 動機・背景
  description: なぜこの機能が必要なのか、どんな問題を解決するのかを書いてください
  placeholder: 例）期限を設定してリマインダー的に使いたいため
  validations:
  required: true

- type: textarea
  id: idea
  attributes:
  label: 実装イメージ（任意）
  placeholder: 例）タスク追加画面に「期限日入力フィールド」を追加