/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2017-02-15 17:18:02 UTC)
 * on 2017-04-18 at 01:09:36 UTC 
 * Modify at your own risk.
 */

package com.swolemates.backend.swoleUserApi.model;

/**
 * Model definition for SwoleUser.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the swoleUserApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SwoleUser extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer age;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("baseball_rank")
  private java.lang.Integer baseballRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("baseball_skill")
  private java.lang.Integer baseballSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("basketball_rank")
  private java.lang.Integer basketballRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("basketball_skill")
  private java.lang.Integer basketballSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("football_rank")
  private java.lang.Integer footballRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("football_skill")
  private java.lang.Integer footballSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer height;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("running_rank")
  private java.lang.Integer runningRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("running_skill")
  private java.lang.Integer runningSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("soccer_rank")
  private java.lang.Integer soccerRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("soccer_skill")
  private java.lang.Integer soccerSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("swimming_rank")
  private java.lang.Integer swimmingRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("swimming_skill")
  private java.lang.Integer swimmingSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("volleyball_rank")
  private java.lang.Integer volleyballRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("volleyball_skill")
  private java.lang.Integer volleyballSkill;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer weight;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("weightlifting_rank")
  private java.lang.Integer weightliftingRank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("weightlifting_skill")
  private java.lang.Integer weightliftingSkill;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAge() {
    return age;
  }

  /**
   * @param age age or {@code null} for none
   */
  public SwoleUser setAge(java.lang.Integer age) {
    this.age = age;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getBaseballRank() {
    return baseballRank;
  }

  /**
   * @param baseballRank baseballRank or {@code null} for none
   */
  public SwoleUser setBaseballRank(java.lang.Integer baseballRank) {
    this.baseballRank = baseballRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getBaseballSkill() {
    return baseballSkill;
  }

  /**
   * @param baseballSkill baseballSkill or {@code null} for none
   */
  public SwoleUser setBaseballSkill(java.lang.Integer baseballSkill) {
    this.baseballSkill = baseballSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getBasketballRank() {
    return basketballRank;
  }

  /**
   * @param basketballRank basketballRank or {@code null} for none
   */
  public SwoleUser setBasketballRank(java.lang.Integer basketballRank) {
    this.basketballRank = basketballRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getBasketballSkill() {
    return basketballSkill;
  }

  /**
   * @param basketballSkill basketballSkill or {@code null} for none
   */
  public SwoleUser setBasketballSkill(java.lang.Integer basketballSkill) {
    this.basketballSkill = basketballSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getFootballRank() {
    return footballRank;
  }

  /**
   * @param footballRank footballRank or {@code null} for none
   */
  public SwoleUser setFootballRank(java.lang.Integer footballRank) {
    this.footballRank = footballRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getFootballSkill() {
    return footballSkill;
  }

  /**
   * @param footballSkill footballSkill or {@code null} for none
   */
  public SwoleUser setFootballSkill(java.lang.Integer footballSkill) {
    this.footballSkill = footballSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getHeight() {
    return height;
  }

  /**
   * @param height height or {@code null} for none
   */
  public SwoleUser setHeight(java.lang.Integer height) {
    this.height = height;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public SwoleUser setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public SwoleUser setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRunningRank() {
    return runningRank;
  }

  /**
   * @param runningRank runningRank or {@code null} for none
   */
  public SwoleUser setRunningRank(java.lang.Integer runningRank) {
    this.runningRank = runningRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRunningSkill() {
    return runningSkill;
  }

  /**
   * @param runningSkill runningSkill or {@code null} for none
   */
  public SwoleUser setRunningSkill(java.lang.Integer runningSkill) {
    this.runningSkill = runningSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSoccerRank() {
    return soccerRank;
  }

  /**
   * @param soccerRank soccerRank or {@code null} for none
   */
  public SwoleUser setSoccerRank(java.lang.Integer soccerRank) {
    this.soccerRank = soccerRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSoccerSkill() {
    return soccerSkill;
  }

  /**
   * @param soccerSkill soccerSkill or {@code null} for none
   */
  public SwoleUser setSoccerSkill(java.lang.Integer soccerSkill) {
    this.soccerSkill = soccerSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSwimmingRank() {
    return swimmingRank;
  }

  /**
   * @param swimmingRank swimmingRank or {@code null} for none
   */
  public SwoleUser setSwimmingRank(java.lang.Integer swimmingRank) {
    this.swimmingRank = swimmingRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSwimmingSkill() {
    return swimmingSkill;
  }

  /**
   * @param swimmingSkill swimmingSkill or {@code null} for none
   */
  public SwoleUser setSwimmingSkill(java.lang.Integer swimmingSkill) {
    this.swimmingSkill = swimmingSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getVolleyballRank() {
    return volleyballRank;
  }

  /**
   * @param volleyballRank volleyballRank or {@code null} for none
   */
  public SwoleUser setVolleyballRank(java.lang.Integer volleyballRank) {
    this.volleyballRank = volleyballRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getVolleyballSkill() {
    return volleyballSkill;
  }

  /**
   * @param volleyballSkill volleyballSkill or {@code null} for none
   */
  public SwoleUser setVolleyballSkill(java.lang.Integer volleyballSkill) {
    this.volleyballSkill = volleyballSkill;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getWeight() {
    return weight;
  }

  /**
   * @param weight weight or {@code null} for none
   */
  public SwoleUser setWeight(java.lang.Integer weight) {
    this.weight = weight;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getWeightliftingRank() {
    return weightliftingRank;
  }

  /**
   * @param weightliftingRank weightliftingRank or {@code null} for none
   */
  public SwoleUser setWeightliftingRank(java.lang.Integer weightliftingRank) {
    this.weightliftingRank = weightliftingRank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getWeightliftingSkill() {
    return weightliftingSkill;
  }

  /**
   * @param weightliftingSkill weightliftingSkill or {@code null} for none
   */
  public SwoleUser setWeightliftingSkill(java.lang.Integer weightliftingSkill) {
    this.weightliftingSkill = weightliftingSkill;
    return this;
  }

  @Override
  public SwoleUser set(String fieldName, Object value) {
    return (SwoleUser) super.set(fieldName, value);
  }

  @Override
  public SwoleUser clone() {
    return (SwoleUser) super.clone();
  }

}
