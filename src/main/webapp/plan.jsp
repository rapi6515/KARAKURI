<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!doctype html>
    <html lang="ja">

    <head>
        <!-- 必須のメタタグ -->
        <meta charset="utf-8">
        <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
        <title>プラン登録</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    </head>

    <body>
        <!--
	<form action="/(プロジェクト名)/(クラス名)" method="post">
    -->
        <div class="container w-80" style="max-width: 80%;">

            <!-- ここから内容 -->

            <h3>プラン登録フォーム</h3>

            <!-- プランID -->
            <div class="col-mb-3">
                <label for="plan_id" class="form-label">プランID</label>
                <input type="text" class="form-control" id="plan_id" name="plan_id">
            </div>

            <!-- プラン名 -->
            <div class="col-mb-3">
                <label for="plan_name" class="form-label">プラン名</label>
                <input type="text" class="form-control" id="plan_name" name="plan_name">
            </div>

            <!-- 単価 -->
            <div class="col-mb-3">
                <label for="cost" class="form-label">単価</label>
                <input type="text" class="form-control" id="cost" name="cost">
            </div>

            <!-- 在庫 -->
            <div class="col-mb-3">
                <label for="stock" class="form-label">在庫</label>
                <input type="text" class="form-control" id="stock" name="stock">
            </div>

            <!-- サブプラン名 -->
            <div class="col-mb-3">
                <label for="subplan" class="form-label">サブプラン</label>
                <input type="text" class="form-control" id="subplan" name="subplan">
            </div>

            <!-- プラン内容 -->
            <div class="col-mb-3">
                <label for="contents" class="form-label">プラン内容</label>
                <textarea class="form-control" cols="1000" rows="10" id="contents" name="contents">
                </textarea>
            </div>

            <!------------------------------------------------------------------------------------>
            <div class="row">
                <!-- 正味所要 -->
                <div class="col-sm-6">
                    <label for="time_required" class="form-label">正味所要時間</label>
                    <input type="text" class="form-control" id="time_required" name="time_required">
                </div>

                <!-- プラン所要時間 -->
                <div class="col-sm-6">
                    <label for="plan_required" class="form-label">プラン所要時間</label>
                    <input type="text" class="form-control" id="plan_required" name="plan_required">
                </div>
            </div>
            <!------------------------------------------------------------------------------------>

            <!-- 必要職種 -->
            <div class="col-mb-3">
                <label for="job_id" class="form-label">必須職種ID</label>
                <input type="text" class="form-control" id="job_id" name="job_id">
            </div>

            <!-- 開催期間 -->
            <div class="col-mb-3">
                <label for="period" class="form-label">開催期間</label>
                <input type="text" class="form-control" id="period" name="period">
            </div>

            <!-- スタッフメモ -->
            <div class="col-mb-3">
                <label for="staff_memo" class="form-label">スタッフメモ</label>
                <textarea class="form-control" cols="100" rows="2" id="staff_memo" name="staff_memo"
                    placeholder="お客様へは表示されません">
                </textarea>
            </div>

            <button type="button" class="btn btn-outline-secondary">登録</button>
            <button type="button" class="btn btn-outline-primary">リセット</button>
            <button type="button" class="btn btn-outline-danger">削除</button>

            <!-- 作成者 -->
            <div class="col-mb-3">
                <label for="createdby" class="form-label">作成者</label>
                <input type="text" class="form-control" id="createdby" name="createdby">
            </div>

            <!-- 作成日 -->
            <div class="col-mb-3">
                <label for="createddate" class="form-label">作成日</label>
                <input type="text" class="form-control" id="createddate" name="createddate">
            </div>

            <!-- 更新者 -->
            <div class="col-mb-3">
                <label for="updatedby" class="form-label">更新者</label>
                <input type="text" class="form-control" id="updatedby" name="updatedby">
            </div>

            <!-- 更新日 -->
            <div class="col-mb-3">
                <label for="updateddate" class="form-label">更新日</label>
                <input type="text" class="form-control" id="updateddate" name="updateddate">
            </div>















            <!-- 内容ここまで -->

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
                crossorigin="anonymous">

                </script>
    </body>

    </html>